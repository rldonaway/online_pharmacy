package com.insightdataengineering.donaway.fileio;

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
// TODO generalize to "any" input... there is nothing special about file
public interface FileInput extends Iterable<String>, AutoCloseable {

	@Override
	/** Overriding the close method from AutoCloseable since checked exceptions should be suppressed. */
	public void close();

}