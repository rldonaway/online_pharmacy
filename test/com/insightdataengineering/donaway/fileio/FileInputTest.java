package com.insightdataengineering.donaway.fileio;

import org.junit.jupiter.api.Test;

public class FileInputTest {

	private FileInput createFileInput() {
		return new FileInputBasic("/Users/rldonaway/git/online_pharmacy/insight_testsuite/tests/test_1/input/itcont.txt");
	}

	@Test
	public void testCreatingFileInput() {
		assert null != createFileInput();
	}

	@Test
	public void testIterating() {
		try (FileInput fi = createFileInput()) {
			for (String line : fi) {
				System.out.println(line);
			}			
		}
	}

}
