package com.insightdataengineering.donaway.inputoutput;

/**
 * Encapsulates the details of text file input. Given a filename, it reads the contents of the file
 * line by line and exposes an {@code Iterable} that can be used by clients to process
 * the lines. This can be useful when it is impossible or unwise to read the entire file into
 * memory at once. It does not throw any checked exceptions, using the presumption that the
 * client cannot or will choose not to handle them, in favor of terminating the process. 
 * 
 * @author Robert L. Donaway
 *
 */
public interface InputResource extends Iterable<String>, AutoCloseable {

	@Override
	/** Overriding the close method from AutoCloseable since checked exceptions will be suppressed. */
	public void close();

}