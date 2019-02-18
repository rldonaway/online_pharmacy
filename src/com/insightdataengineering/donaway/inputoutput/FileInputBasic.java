package com.insightdataengineering.donaway.inputoutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the InputResource interface using basic file input libraries provided by the JDK.
 * 
 * @author Robert L. Donaway
 */
public class FileInputBasic implements InputResource {

	private Logger log = Logger.getLogger("com.insightengineering.donaway.inputoutput.FileInputBasic");

	private FileInputStream inputStream;
	Scanner scanner;
	private FileInputIterator fileIterator;
	private String filePath;

	public FileInputBasic(String filePath, boolean skipHeader) {
		this.filePath = filePath;
		try {
			inputStream = new FileInputStream(filePath);
			scanner = new Scanner(inputStream);
			fileIterator = new FileInputIterator(this, skipHeader);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(String.format("Requested file (%s) could not be found.", filePath));
		}
	}

	/* (non-Javadoc)
	 * @see com.insightdataengineering.donaway.inputoutput.InputResource#close()
	 */
	@Override
	public void close() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.log(Level.WARNING, String.format("Could not close input stream from file %s", this.filePath), e);
			}
		}
		if (scanner != null) {
			scanner.close();
		}
	}

	/* (non-Javadoc)
	 * @see com.insightdataengineering.donaway.inputoutput.InputResource#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return fileIterator;
	}
	
	@Override
	public String toString() {
		return String.format("[FileInput filePath=%s inputStream=%s scanner=%s]", 
				filePath, inputStream, scanner);
	}

}
