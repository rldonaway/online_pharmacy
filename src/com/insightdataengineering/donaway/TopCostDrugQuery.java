package com.insightdataengineering.donaway;

import com.insightdataengineering.donaway.inputoutput.FileInputBasic;
import com.insightdataengineering.donaway.inputoutput.FileOutputBasic;
import com.insightdataengineering.donaway.inputoutput.InputResource;
import com.insightdataengineering.donaway.inputoutput.OutputResource;
import com.insightdataengineering.donaway.statsgenerator.DrugPrescriberStats;
import com.insightdataengineering.donaway.statsgenerator.StatsGenerator;

public class TopCostDrugQuery {
	
	public static void main(String... args) {
		String inputFilePath = args[0];
		try (InputResource fileInput = new FileInputBasic(inputFilePath)) {
			StatsGenerator stats = new DrugPrescriberStats();
			stats.generate(fileInput);
		}
		String outputFilePath = args[1];
		try (OutputResource fileOutput = new FileOutputBasic(outputFilePath)) {
			
		}
	}
	
}
