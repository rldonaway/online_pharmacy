package com.insightdataengineering.donaway;

import com.insightdataengineering.donaway.fileio.FileInput;
import com.insightdataengineering.donaway.fileio.FileInputBasic;
import com.insightdataengineering.donaway.statsgenerator.DrugPrescriberStats;
import com.insightdataengineering.donaway.statsgenerator.StatsGenerator;

public class TopCostDrugQuery {
	
	public static void main(String... args) {
		String inputFilePath = args[0];
		try (FileInput fileInput = new FileInputBasic(inputFilePath)) {
			StatsGenerator stats = new DrugPrescriberStats();
			stats.generate(fileInput);
			// 7. stream to shoutput fahl? or just keep in memory?
		}
		// 8. finalize file io class
		// 9. finalize file writing class, if already open, otherwise write the dam and then close
	}
	
}
