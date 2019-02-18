package com.insightdataengineering.donaway.inputoutput;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.insightdataengineering.donaway.inputoutput.FileInputBasic;
import com.insightdataengineering.donaway.inputoutput.InputResource;

public class FileInputTest {

	private InputResource createFileInput() {
//	    String filePath = "/Users/rldonaway/git/online_pharmacy/insight_testsuite/tests/test_1/input/itcont.txt";
        String filePath = "C:\\Users\\Terri\\git\\online_pharmacy\\insight_testsuite\\tests\\test_1\\input\\itcont.txt";
		return new FileInputBasic(filePath, true);
	}

	@Test
	public void testCreatingFileInput() {
		assert null != createFileInput();
	}

	@Test
	public void testIterating() {
		List<String> fileLines = Arrays.asList(
				"id,prescriber_last_name,prescriber_first_name,drug_name,drug_cost",
				"1000000001,Smith,James,AMBIEN,100",
				"1000000002,Garcia,Maria,AMBIEN,200",
				"1000000003,Johnson,James,CHLORPROMAZINE,1000",
				"1000000004,Rodriguez,Maria,CHLORPROMAZINE,2000",
				"1000000005,Smith,David,BENZTROPINE MESYLATE,1500");
		try (InputResource fi = createFileInput()) {
			for (String line : fi) {
				assert fileLines.contains(line);
			}			
		}
	}

}
