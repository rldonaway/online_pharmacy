package com.insightdataengineering.donaway;

import com.insightdataengineering.donaway.inputoutput.FileInputBasic;
import com.insightdataengineering.donaway.inputoutput.FileOutputBasic;
import com.insightdataengineering.donaway.inputoutput.InputResource;
import com.insightdataengineering.donaway.inputoutput.OutputResource;
import com.insightdataengineering.donaway.query.DrugPrescriberQuery;
import com.insightdataengineering.donaway.query.Query;

public class TopCostDrugQuery {
	
    /**
     * Uses a FileInputBasic to read a file with the given path (arg0). The file should be CSV 
     * and may contain a header row equal to "drug_name,num_prescriber,total_cost" which 
     * indicates the meanings of the columns. If no header row is present, send false for
     * arg2. Then the {@code DrugPrescriberQuery} is run on the data, producing an output 
     * file with the given path (arg1).
     *
     * @param args inputFilePath outputFilePath hasHeaderRow (opt.)
     * 
     * @author Robert L. Donaway
     */
	public static void main(String... args) {
		String inputFilePath = args[0];
		String outputFilePath = args[1];
		boolean hasHeaderRow = true;
		if (args.length > 2 && args[2] != null) {
		    hasHeaderRow = Boolean.getBoolean(args[2]);
		}
		try (
		        InputResource fileInput = new FileInputBasic(inputFilePath, hasHeaderRow); 
		        OutputResource fileOutput = new FileOutputBasic(outputFilePath)
		    )
		{
			Query query = new DrugPrescriberQuery();
			query.generateResults(fileInput, fileOutput);
		}
	}
	
//	input C:/Users/Terri/git/online_pharmacy/insight_testsuite/tests/test_2/input/de_cc_data.txt
//	output C:/Users/Terri/git/online_pharmacy/insight_testsuite/tests/test_2/output/top_cost_drug.txt
}
