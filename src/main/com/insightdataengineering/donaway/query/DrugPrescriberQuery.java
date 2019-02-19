package com.insightdataengineering.donaway.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.insightdataengineering.donaway.inputoutput.OutputResource;

/**
 * Generates a list of all drugs, the total number of UNIQUE individuals who prescribed
 * the medication, and the total drug cost, which is listed in descending order
 * based on the total drug cost and if there is a tie, drug name in ascending order.
 *  
 * @author Robert L. Donaway
 */
public class DrugPrescriberQuery implements Query {

    private Logger log = Logger.getLogger("com.insightengineering.donaway.query.DrugPrescriberQuery");

	private Map<String, PrescriberCost> drugMap = new HashMap<>();
	private DrugPrescriberQueryDataExtractor dataExtractor = new DrugPrescriberQueryDataExtractor();

	/*
	 * (non-Javadoc)
	 *  @see com.insightdataengineering.donaway.query.Query#generateResults()
	 */
	@Override
	public void generateResults(Iterable<String> input, OutputResource output) {
		for (String line : input) {
			processLine(line);
		}
		List<DrugPrescriberCost> dpcResult = new ArrayList<>(drugMap.size());
		for (Entry<String, PrescriberCost> drugEntry : drugMap.entrySet()) {
			String drug = drugEntry.getKey();
            PrescriberCost prescriberCost = drugMap.get(drug);
			int prescriberCount = prescriberCost.prescriberCount();
			BigDecimal totalCost = prescriberCost.cost;
			if (log.isLoggable(Level.FINEST)) {
			    log.log(Level.FINEST, String.format("%s,%s,%s", drug, prescriberCount, totalCost));
			}
			dpcResult.add(new DrugPrescriberCost(drug, prescriberCount, totalCost));
		}
		Collections.sort(dpcResult, new Comparator<DrugPrescriberCost>() {
            @Override
            public int compare(DrugPrescriberCost o1, DrugPrescriberCost o2) {
                assert null != o1 && null != o2;
                int costComp = o1.totalCost.compareTo(o2.totalCost);
                if (costComp == 0) {
                    return o1.drug.compareTo(o2.drug);
                }
                return -1 * costComp;
            }
        });
		output.writeCsvLine("drug_name", "num_prescriber", "total_cost");
		for (DrugPrescriberCost dpc : dpcResult) {
		    output.writeCsvLine(dpc.drug, dpc.prescriberCount, dpc.totalCost);
		}
	}

	void processLine(String line) {
		if (log.isLoggable(Level.FINER)) {
			log.log(Level.FINER, "Processing: " + line);
		}
		assert null != line;
		String[] lineItems = dataExtractor.extractLineItems(line);
		
		String prescriber = lineItems[0];
		
		String drug = lineItems[1];
		if (empty(prescriber, "drug", drug)) {
			return; // drug is required for query
		}
		
		String costString = lineItems[2];
		if (empty(prescriber, "cost", costString)) {
			return; // cost is required for query
		}
		BigDecimal cost = null;
		try {
			cost = new BigDecimal(costString);
		} catch (NumberFormatException nfe) {
			log.log(Level.WARNING, String.format("Row with prescriber=%s has invalid cost=%s", prescriber, costString));
			return; // cost is required for query
		}
		
		addToDrugMap(drug, cost, prescriber);
	}

    private boolean empty(String id, String label, String value) {
		if (value == null || value.length() < 1) {
		    if (log.isLoggable(Level.WARNING)) {
		        log.log(Level.WARNING, String.format("Row for %s has %s=%s", id, label, value));
		    }
			return true;
		}
		return false;
	}

	private void addToDrugMap(String drug, BigDecimal cost, String prescriber) {
		if (drugMap.containsKey(drug)) {
			drugMap.get(drug).add(prescriber, cost);
		} else {
			PrescriberCost newOne = new PrescriberCost();
			newOne.add(prescriber, cost);
			drugMap.put(drug, newOne);
		}
	}

	final class PrescriberCost {
		final Set<String> prescribers = new HashSet<>();
		BigDecimal cost = BigDecimal.ZERO;

		void add(String prescriber, BigDecimal cost) {
			this.prescribers.add(prescriber);
			this.cost = this.cost.add(cost);
		}

		int prescriberCount() {
			return prescribers.size();
		}

		@Override
		public String toString() {
			return String.format("PrescriberCost [prescribers=%s, cost=%s]", prescribers, cost);
		}

	}

	final class DrugPrescriberCost {
		final String drug;
		final int prescriberCount;
		final BigDecimal totalCost;

		public DrugPrescriberCost(String drug, int prescriberCount, BigDecimal totalCost) {
			this.drug = drug;
			this.prescriberCount = prescriberCount;
			this.totalCost = totalCost;
		}

		@Override
		public String toString() {
			return String.format("DrugPrescriberCost [drug=%s, prescriberCount=%s, totalCost=%s]", drug, prescriberCount, totalCost);
		}

	}

}
