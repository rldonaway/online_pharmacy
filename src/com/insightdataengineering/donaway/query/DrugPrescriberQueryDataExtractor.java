package com.insightdataengineering.donaway.query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deals with rows like this:
 * 
  1275618837,
  "BOYD, 
  JR",
  KEITH,
  IBUPROFEN,
  58.79
 */
public class DrugPrescriberQueryDataExtractor {

    private Logger log = Logger.getLogger("com.insightengineering.donaway.query.DrugPrescriberQueryDataExtractor");

    static final String COMMA = ",";
    static final String QUOTE = "\"";

    String[] extractLineItems(String line) {
        String[] lineItems = null;
        if (line.indexOf('"') < 0) {
            lineItems = line.split(COMMA);
            if (lineItems.length != 5) {
                log.log(Level.WARNING, "Row does not have 5 entries: " + line);
            }
        } else {
            lineItems = parseLineWithQuotes(line);
        }
        return lineItems;
    }

    String[] parseLineWithQuotes(String line) {
        List<String> result = new ArrayList<>();
        String[] parts = splitOnFirstComma(line);
        result.add(parts[0]); //0
        parts = splitOnFirstCommaQuotes(parts[1]);
        result.add(parts[0]); //1
        parts = splitOnFirstCommaQuotes(parts[1]);
        result.add(parts[0]); //2
        parts = splitOnFirstCommaQuotes(parts[1]);
        result.add(parts[0]); //3
        result.add(parts[1]); //4
        return result.toArray(new String[5]);
    }

    String[] splitOnFirstCommaQuotes(String string) {
        int quoteSpot = string.indexOf(QUOTE);
        if (quoteSpot != 0) {
            return splitOnFirstComma(string);
        }
        String[] result = new String[2];
        int start = 0;
        int end = string.indexOf('"', start + 1);
        result[0] = string.substring(start, end + 1);
        result[1] = string.substring(end + 2);
        return result;
    }

    String[] splitOnFirstComma(String line) {
        int commaSpot = line.indexOf(',');
        String[] result = new String[2];
        result[0] = line.substring(0, commaSpot);
        result[1] = line.substring(commaSpot + 1);
        return result;
    }

}
