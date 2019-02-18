package com.insightdataengineering.donaway.query;

import org.junit.jupiter.api.Test;

import com.insightdataengineering.donaway.inputoutput.FileInputBasic;
import com.insightdataengineering.donaway.inputoutput.InputResource;
import com.insightdataengineering.donaway.inputoutput.OutputResource;

public class DrugPrescriberQueryBigTest {

    @Test
	public void testGenerateBigData() {
//      String filePath = "/Users/rldonaway/git/online_pharmacy/insight_testsuite/tests/test_1/input/itcont.txt";
        String filePath = "C:\\Users\\Terri\\git\\online_pharmacy\\insight_testsuite\\tests\\test_2\\input\\de_cc_data.txt";
		DrugPrescriberQuery dpsToTest = new DrugPrescriberQuery();
        try (InputResource fileInput = new FileInputBasic(filePath, true)) { 
            dpsToTest.generateResults(fileInput, new OutputResource() {
                @Override public void writeLine(String line) { System.out.println(line); }
                @Override public void writeCsvLine(Object... items) {
                    for (Object item : items) {
                        System.out.print(item + ","); 
                    }
                    System.out.println();
                }
                @Override public void close() { }
            });
        }
    }
	
}
