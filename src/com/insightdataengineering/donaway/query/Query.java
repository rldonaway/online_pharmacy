package com.insightdataengineering.donaway.query;

import com.insightdataengineering.donaway.inputoutput.OutputResource;

/** 
 * Uses an {@code Iterable} object to obtain row data and perform some processing on 
 * it to generate query results, which are written to the given {@code OutputResource}.
 *
 * @author Robert L. Donaway
 */
public interface Query {

	void generateResults(Iterable<String> input, OutputResource output);

}