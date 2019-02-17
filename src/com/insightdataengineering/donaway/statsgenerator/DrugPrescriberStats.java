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

	public DrugPrescriberStats() {
		// TODO Auto-generated constructor stub
	}

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
//		
//		CHLORPROMAZINE,2,3000
//		BENZTROPINE MESYLATE,1,1500
//		AMBIEN,2,300
		List<DrugPrescriberCost> result = new ArrayList<DrugPrescriberCost>(drugMap.size());
//		result.add("drug_name,num_prescriber,total_cost"); // TODO put this into fahl
		for (String drug : drugMap.keySet()) {
			PrescriberCost prescriberCost = drugMap.get(drug);
			int prescriberCount = prescriberCost.prescriberCount();
			BigDecimal totalCost = prescriberCost.cost;
			System.out.println(String.format("%s,%s,%s", drug, prescriberCount, totalCost));
			result.add(new DrugPrescriberCost(drug, prescriberCount, totalCost));
		}
		Collections.sort(result); // maybe ass in a method
		for (DrugPrescriberCost dpc : result) {
			System.out.println(dpc);
		}
	}

	void processLine(String line) {
		if (log.isLoggable(Level.FINER)) {
			log.log(Level.FINER, "Processing: " + line);
		}
		assert null != line;
		String[] lineItems = line.split(",");
		if (lineItems.length != 5) {
			// TODO log error with id
			return;
		}
		String id = lineItems[0];
		// TODO check non empty
		String lastName = lineItems[1];
		// TODO check non empty
		String firstName = lineItems[2];
		// TODO check non empty
		String drug = lineItems[3];
		// TODO check non empty
		String costString = lineItems[4];
		// TODO check non empty
		BigDecimal cost = new BigDecimal(costString);
		// TODO catch and log parsing problem
		String prescriber = getName(lastName, firstName);
		addToDrugMap(drug, cost, prescriber);
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
		final BigDecimal totalCost;

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
