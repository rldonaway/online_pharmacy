package com.insightdataengineering.donaway.inputoutput;

public class FileOutputBasic implements OutputResource {

	private String filePath;

	public FileOutputBasic(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
