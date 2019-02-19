package com.insightdataengineering.donaway.inputoutput;

/**
 * Encapsulates the details of text file output. Given a filename, it permits writing lines
 * to the file. It does not throw any checked exceptions, using the presumption that the
 * client cannot or will choose not to handle them, in favor of terminating the process
 * or logging an error. 
 * 
 * @author Robert L. Donaway
 */
public interface OutputResource extends AutoCloseable {

    void writeLine(String line);

	/**
	 * Writes the given items, comma separated, to a file line.
	 */
    void writeCsvLine(Object... items);
    
    @Override
	/** Overriding the close method from AutoCloseable since checked exceptions will be suppressed. */
	void close();

}
