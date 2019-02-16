package com.insightdataengineering.donaway.fileio;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Scanner;

public class FileInputIterator implements Iterator<String> {

	FileInputIterator(FileInput fileIn) {
		FileInputStream m = fileIn.inputStream;
		Scanner n = fileIn.sc;
		// don't need liz... can just use dems wae yer needz them
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
