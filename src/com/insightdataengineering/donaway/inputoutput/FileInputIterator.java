package com.insightdataengineering.donaway.inputoutput;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permits iteration over the lines provided by a FileInputBasic.
 * 
 * @author Robert L. Donaway
 */
public class FileInputIterator implements Iterator<String> {

	private Logger log = Logger.getLogger("com.insightengineering.donaway.inputoutput.FileInputIterator");

	private FileInputBasic fileIn;
	private boolean skipHeader;
	private boolean firstLineProcessed;
	
	FileInputIterator(FileInputBasic fileIn, boolean skipHeader) {
		assert null != fileIn;
		assert null != fileIn.scanner; 
		this.fileIn = fileIn;
		this.skipHeader = skipHeader;
	}
	
	@Override
	public boolean hasNext() {
		return fileIn.scanner.hasNextLine();
	}

	@Override
	public String next() {
		String result = fileIn.scanner.nextLine();
		if (fileIn.scanner.ioException() != null) {
			log.log(Level.SEVERE, String.format("Problem occurred while processing lines of %s.", this.fileIn), 
					fileIn.scanner.ioException());
			fileIn.close();
		}
		if (!firstLineProcessed && skipHeader && fileIn.scanner.hasNextLine()) {
		    log.log(Level.INFO, String.format("Skipping over the first line of %s.", this.fileIn));
		    result = fileIn.scanner.nextLine();
		    firstLineProcessed = true;
		}
		return result;
	}

	@Override
	public String toString() {
		return String.format("[FileInputIterator fileInput=%s]", fileIn);
	}
	
}
