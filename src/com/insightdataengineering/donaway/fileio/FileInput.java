package com.insightdataengineering.donaway.fileio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class FileInput implements Iterable<String> {

	FileInputStream inputStream;
	Scanner sc;
	private FileInputIterator fileIterator;
	private Logger log = Logger.getLogger("com.insightengineering.donaway.fileio");
	private String filePath;

	public FileInput(String filePath) {
		this.filePath = filePath;
		try {
			inputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(String.format("Requested file (%s) could not be found.", filePath));
		} finally {
			closeFile();
		}
		sc = new Scanner(inputStream, "UTF-8");
		fileIterator = new FileInputIterator(this);
	}

	public void processFileLines(String filePath) {
		while (sc.hasNextLine()) {
			String line = sc.nextLine(); // this what we gon send to t'other
			// System.out.println(line);
		}
		// note that Scanner suppresses exceptions, but not sho we gon use
		if (sc.ioException() != null) {
			log.log(Level.SEVERE, String.format("Problem occurred while processing file %s's lines.", this.filePath), sc.ioException());
			closeFile();
		}
	}

	public void closeFile() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.log(Level.WARNING, String.format("Could not close input stream from file %s", this.filePath), e);
			}
		}
		if (sc != null) {
			sc.close();
		}
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		/* QUANNIS! */
		return fileIterator;
	}
	
}
