package com.knockknock.message.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;


public class MessageReader {
	
	private File file;
	private IOException thrownException;


	public MessageReader(ResponseFiles responseFile) {
		getFilePath(responseFile);

	}

	private final void getFilePath(ResponseFiles fileToGet) {
		String packagePath = "/com/knockknock/message";
		
		if (fileToGet == ResponseFiles.CLUES)
			file = new File(getClass().getResource(String.format("%s/clues.txt", packagePath)).getPath());
		else if (fileToGet == ResponseFiles.ANSWERS)
			file = new File(getClass().getResource(String.format("%s/answers.txt", packagePath)).getPath());
		
		if (file == null) {
			throwException(new IOException("Are you missing a file?"));
		}
	}


	public List<String> readFile() {
		List<String> output = new ArrayList<String>();
		
		try (FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
			
			String line = reader.readLine();
			output.add(line);
			
			while (line != null) {
				output.add(line);
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			throwException(e);
		}
		
		return output;
	}

    private void throwException(IOException e) {
		this.thrownException = e;
	}
	
	public synchronized IOException thrownException() {
			return thrownException;
	}
	
}
		
		


