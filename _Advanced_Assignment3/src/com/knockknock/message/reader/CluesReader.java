package com.knockknock.message.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class CluesReader {
	
	File file;
	
	private final void getFilePath() {
		file = new File("clues.txt");
	}

	public static void main (String[] args) throws FileNotFoundException {
		CluesReader cluesReader = new CluesReader();
		cluesReader.getFilePath();
		

		try (FileInputStream fis = new FileInputStream(cluesReader.file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
			
			System.out.println("Reading File line by line using BufferedReader");
			
			String line = reader.readLine();
			
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
