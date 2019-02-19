package com.insightdataengineering.donaway.inputoutput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the OutputResource interface using basic file output libraries provided by the JDK.
 * 
 * @author Robert L. Donaway
 */
public class FileOutputBasic implements OutputResource {

    private Logger log = Logger.getLogger("com.insightengineering.donaway.inputoutput.FileOutputBasic");

	private String filePath;
	private BufferedWriter writer;
	
	public FileOutputBasic(String filePath) {
		this.filePath = filePath;
		Path path = Paths.get(filePath);
		try {
            writer = Files.newBufferedWriter(path);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Problem when opening requested file (%s).", filePath), e);
        }
	}
	
	/* (non-Javadoc)
	 * @see com.insightdataengineering.donaway.inputoutput.OutputResource#writeLine()
	 */
	@Override
    public void writeLine(String line) {
	    try {
            writer.write(line);
        } catch (IOException e) {
            close();
            throw new IllegalStateException(String.format("Problem when writing line [%s] to requested file (%s).", line, filePath), e);
        }
	}
	
	/* (non-Javadoc)
	 * @see com.insightdataengineering.donaway.inputoutput.OutputResource#writeCsvLine()
	 */
	@Override
	public void writeCsvLine(Object... items) {
	    StringBuilder sb = new StringBuilder();
	    for (Object item : items) {
	        assert null != item;
	        sb.append(item);
	        sb.append(',');
	    }
	    sb.deleteCharAt(sb.length() - 1);
	    sb.append(System.lineSeparator());
	    writeLine(sb.toString());
	}
	
	@Override
	public void close() {
		try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, String.format("Problem when attempting to close file %s.", this.filePath), e); 
        }
	}

}
