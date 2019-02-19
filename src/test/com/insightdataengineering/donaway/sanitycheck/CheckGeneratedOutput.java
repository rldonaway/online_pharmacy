package com.insightdataengineering.donaway.sanitycheck;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

//import org.junit.jupiter.api.Test;

public class CheckGeneratedOutput {

//    String filePath = "C:\\Users\\Terri\\git\\online_pharmacy\\insight_testsuite\\tests\\test_2\\input\\de_cc_data.txt";
    String filePath = "./../../insight_testsuite/tests/test_2/input/itcont.txt"; // TODO externalize

//    @Test
    public void searchForLines() throws IOException {
        System.out.println("--- GILDAGIA,19,6324.04 ---");
        printLinesForDrug("GILDAGIA");
        System.out.println("--- NEOSPORIN G.U. IRRIGANT,15,36506.85 ---");
        printLinesForDrug("NEOSPORIN G.U. IRRIGANT");
        System.out.println("--- FLUOROMETHOLONE,5201,16941824.25 ---");
        printLinesForDrug("FLUOROMETHOLONE");
    }

    private void printLinesForDrug(String drug) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            final BigDecimalWrapper total = new BigDecimalWrapper();
            Set<String> doctors = new HashSet<>();
            stream.filter(s -> s.indexOf("," + drug + ",") > -1).forEach(s -> {
                int lastComma = s.lastIndexOf(',');
                String amountString = s.substring(lastComma + 1);
                BigDecimal amount = new BigDecimal(amountString);
                total.add(amount);
                int firstComma = s.indexOf(',');
                String doctor = s.substring(firstComma + 1, lastComma);
                if (doctors.contains(doctor)) {
                    System.out.println("possible duplicate doctor:" + doctor);
                }
                doctors.add(doctor);
            });
            System.out.println("---> total = " + total.currentTotal);
            System.out.println("---> count = " + doctors.size());
        }
    }

    public static final void main(String... args) {
    	try {
			new CheckGeneratedOutput().searchForLines();
			System.out.println("CheckGeneratedOutput finished");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
}
