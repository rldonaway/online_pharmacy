package com.insightdataengineering.donaway.inputoutput;

public interface OutputResource extends AutoCloseable {

	@Override
	/** Overriding the close method from AutoCloseable since checked exceptions will be suppressed. */
	public void close();

}
