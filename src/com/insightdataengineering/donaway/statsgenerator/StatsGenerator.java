package com.insightdataengineering.donaway.statsgenerator;

/** 
 * Uses an {@code Iterable} object to obtain row data and perform some processing on it to generate raw statistics.
 *
 * @author Robert L. Donaway
 */
public interface StatsGenerator {

	void generate(Iterable<String> input);

}