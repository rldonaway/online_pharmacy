package com.insightdataengineering.donaway.statsgenerator;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.insightdataengineering.donaway.inputoutput.OutputResource;
import com.insightdataengineering.donaway.query.DrugPrescriberQuery;

public class DrugPrescriberStatsTest {

	@Test
	public void testGenerateSmallData() {
		DrugPrescriberQuery dpsToTest = new DrugPrescriberQuery();
		List<String> inputData = Arrays.asList(
				"1000000001,Smith,James,AMBIEN,100",
				"1000000002,Garcia,Maria,AMBIEN,200",
				"1000000003,Johnson,James,CHLORPROMAZINE,1000",
				"1000000004,Rodriguez,Maria,CHLORPROMAZINE,2000",
				"1000000005,Smith,David,BENZTROPINE MESYLATE,1500");
		dpsToTest.generateResults(inputData, new OutputResource() {
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
