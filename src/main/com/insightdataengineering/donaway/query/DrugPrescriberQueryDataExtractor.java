package com.insightdataengineering.donaway.query;

/**
 * Deals with rows like this:
 * 
  1275618837,"BOYD, JR",KEITH,IBUPROFEN,58.79
 */
public class DrugPrescriberQueryDataExtractor {

    String[] extractLineItems(String line) {
    	String[] result = new String[3];
    	int lastCommaSpot = line.lastIndexOf(',');
    	assert lastCommaSpot > -1 && lastCommaSpot < line.length();
    	result[2] = line.substring(lastCommaSpot + 1);
    	String prefix = line.substring(0, lastCommaSpot);
    	lastCommaSpot = prefix.lastIndexOf(',');
    	assert lastCommaSpot > -1 && lastCommaSpot < prefix.length();
    	result[1] = prefix.substring(lastCommaSpot + 1);
    	result[0] = prefix.substring(0, lastCommaSpot);
    	return result;
    }

}
