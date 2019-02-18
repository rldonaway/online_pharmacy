package com.insightdataengineering.donaway.statsgenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrugPrescriberStats implements StatsGenerator {

	private Logger log = Logger.getLogger("com.insightengineering.donaway.statsgenerator");

	private Map<String, PrescriberCost> drugMap = new HashMap<String, PrescriberCost>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.insightdataengineering.donaway.statsgenerator.StatsGenerator#generate()
	 */
	@Override
	public void generate(Iterable<String> input) {
		for (String line : input) {
			processLine(line);
		}
		System.out.println(drugMap);
		List<DrugPrescriberCost> dpcResult = new ArrayList<DrugPrescriberCost>(drugMap.size());
		for (String drug : drugMap.keySet()) {
			PrescriberCost prescriberCost = drugMap.get(drug);
			int prescriberCount = prescriberCost.prescriberCount();
			BigDecimal totalCost = prescriberCost.cost;
			log.log(Level.FINEST, String.format("%s,%s,%s", drug, prescriberCount, totalCost));
			dpcResult.add(new DrugPrescriberCost(drug, prescriberCount, totalCost));
		}
		Collections.sort(dpcResult); // TODO allow for client to pass in a sort method?
		for (DrugPrescriberCost dpc : dpcResult) {
			System.out.println(dpc);
		}
		//TODO do something real with the results... return?
//		result.add(0, "drug_name,num_prescriber,total_cost");
	}

	void processLine(String line) {
		if (log.isLoggable(Level.FINER)) {
			log.log(Level.FINER, "Processing: " + line);
		}
		assert null != line;
		String[] lineItems = line.split(",");
		if (lineItems.length != 5) {
			log.log(Level.WARNING, "Row does not have 5 entries: " + line);
			return;
		}
		String id = lineItems[0];
		if (empty(id, "id", id)) {
			return;
		}
		String lastName = lineItems[1];
		if (empty(id, "lastName", lastName)) {
			return;
		}
		String firstName = lineItems[2];
		if (empty(id, "firstName", firstName)) {
			return;
		}
		String drug = lineItems[3];
		if (empty(id, "drug", drug)) {
			return;
		}
		String costString = lineItems[4];
		if (empty(id, "cost", costString)) {
			return;
		}
		BigDecimal cost = null;
		try {
			cost = new BigDecimal(costString);
		} catch (NumberFormatException nfe) {
			log.log(Level.WARNING, String.format("Row with id=%s has invalid cost=%s", id, costString));
		}
		String prescriber = getName(lastName, firstName);
		if (empty(id, "prescriber", prescriber)) {
			return;
		}
		addToDrugMap(drug, cost, prescriber);
	}

	private boolean empty(String id, String label, String value) {
		if (value == null || value.length() < 1) {
			log.log(Level.WARNING, String.format("Row with id=%s has %s=%s", id, label, value));
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

	private String getName(String lastName, String firstName) {
		return String.format("\"%s, %s\"", lastName, firstName);
	}

	final class PrescriberCost {
		Set<String> prescribers = new HashSet<String>();
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

	final class DrugPrescriberCost implements Comparable<DrugPrescriberCost> {
		
		final String drug;
		final int prescriberCount;
		final BigDecimal totalCost; // TODO should this be an integer?

		public DrugPrescriberCost(String drug, int prescriberCount, BigDecimal totalCost) {
			this.drug = drug;
			this.prescriberCount = prescriberCount;
			this.totalCost = totalCost;
		}

		@Override
		public int compareTo(DrugPrescriberCost o) {
			assert null != o;
			int costComp = this.totalCost.compareTo(o.totalCost);
			if (costComp == 0) {
				return this.drug.compareTo(o.drug);
			}
			return -1 * costComp;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((drug == null) ? 0 : drug.hashCode());
			result = prime * result + prescriberCount;
			result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DrugPrescriberCost other = (DrugPrescriberCost) obj;
			if (drug == null) {
				if (other.drug != null) {
					return false;
				}
			} else if (!drug.equals(other.drug)) {
				return false;
			}
			if (prescriberCount != other.prescriberCount) {
				return false;
			}
			if (totalCost == null) {
				if (other.totalCost != null) {
					return false;
				}
			} else if (!totalCost.equals(other.totalCost)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return String.format("DrugPrescriberCost [drug=%s, prescriberCount=%s, totalCost=%s]", drug, prescriberCount, totalCost);
		}

	}

}
