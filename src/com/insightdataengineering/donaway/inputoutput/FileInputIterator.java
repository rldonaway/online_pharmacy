package com.insightdataengineering.donaway.inputoutput;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileInputIterator implements Iterator<String> {

	private Logger log = Logger.getLogger("com.insightengineering.donaway.fileio.FileInputIterator");

	private FileInputBasic fileIn;
	
	FileInputIterator(FileInputBasic fileIn) {
		assert null != fileIn;
		assert null != fileIn.scanner; 
		this.fileIn = fileIn;
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
		return result;
	}

	@Override
	public String toString() {
		return String.format("[FileInputIterator fileInput=%s]", fileIn);
	}
	
}
