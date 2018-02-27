package com.knockknock.message.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JOptionPane;

import com.knockknock.exceptions.CustExcep;
import com.knockknock.exceptions.ExceptionHandler;

/**
* <h1>Message Reader</h1>
* Helper class that is reads text files line by line and returns each line as 
* a new String in a List
* <p>
* <b>Note:</b> Text files must have predefined names and must be located at specific folder
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class MessageReader {
	
	private File file;

	/**
	 * Class constructor that accepts Enum as a type of file to load
	 * @param responseFile
	 */
	public MessageReader(ResponseFiles responseFile) {
		getFilePath(responseFile);

	}

	/**
	 * Method that assigns an absolute file path to internal field
	 * @param fileToGet type of file to load as specified in the Enum class {@link #getFilePath(ResponseFiles)}
	 */
	private final void getFilePath(ResponseFiles fileToGet) {
		String cluesFile = "clues.txt";
		String answersFile = "answers.txt";
		
		// Try to locate and get absolute path of either clues or answer files. If files do not exist
		// an error is displayed to the user and application is closed.
		try {
		if (fileToGet == ResponseFiles.CLUES)
			file = new File(cluesFile);
		else if (fileToGet == ResponseFiles.ANSWERS) 
			file = new File(answersFile);
		} catch (NullPointerException e) {
			ExceptionHandler.handleException(e);
			//javax.swing.JOptionPane.showMessageDialog(null, "Jokes Files Missing", "File Missing", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}


	/**
	 * Method to read text file and return each line as new String in a List
	 * @return List containing each line in the text file
	 */
	public List<String> readFile() {
		List<String> output = new ArrayList<String>();
		
		// Read file until there are no more lines. In case of error reading display message to user.
		try (FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
			
			String line = reader.readLine();
			output.add(line);
			
			while (line != null) {
				output.add(line);
				line = reader.readLine();
			}
			
		} catch (IOException e) {
			ExceptionHandler.handleException(e);
			//javax.swing.JOptionPane.showMessageDialog(null, "Cannot read jokes file(s)", "Read Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return output;
	}
}
		
		


