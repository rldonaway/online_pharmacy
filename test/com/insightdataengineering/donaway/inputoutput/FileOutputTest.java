package com.insightdataengineering.donaway.inputoutput;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FileOutputTest {

    private OutputResource createFileOutput() {
//      String filePath = "/Users/rldonaway/git/online_pharmacy/insight_testsuite/tests/test_1/input/itcont.txt";
        String filePath = "C:\\Users\\Terri\\git\\online_pharmacy\\insight_testsuite\\tests\\test_3\\output\\some_file.txt";
        return new FileOutputBasic(filePath);
    }

    @Test
    public void testIterating() {
        List<String> fileLines = Arrays.asList(
                "1000000001,\"Smith,James\",AMBIEN,100" + System.lineSeparator(),
                "1000000002,\"Garcia,Maria\",AMBIEN,200" + System.lineSeparator()
        );
        List<Object[]> csvs = new ArrayList<>();
        csvs.add(new Object[] { 1000000003, "\"Johnson,James\"", "CHLORPROMAZINE", "1000" });
        csvs.add(new Object[] { 1000000004, "\"Rodriguez,Maria\"", "CHLORPROMAZINE", 20.00f });
        csvs.add(new Object[] { 1000000005, "\"Smith,David\"", "BENZTROPINE MESYLATE", new BigDecimal("1500") });
        try (OutputResource fo = createFileOutput()) {
            for (String line : fileLines) {
                fo.writeLine(line);
            }           
            for (Object[] csv : csvs) {
                fo.writeCsvLine(csv);
            }
        }
    }

}
