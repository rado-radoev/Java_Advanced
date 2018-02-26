package com.knockknock.message.reader;

import java.util.List;

/**
* <h1>Message Loader</h1>
* Helper Class that reads text files containing clues and answers for the Knock Knock application.
* 
* Singleton class will be loaded only once during application launch.
* 
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class MessageLoader {
	
	private static List<String> cluesList;
	private static List<String> answersList;
	
	/**
	 * Class Constructor
	 */
	private MessageLoader() { 
		if (cluesList == null)
			setClues(new MessageReader(ResponseFiles.CLUES).readFile());
		if (answersList == null)
			setAnwsers(new MessageReader(ResponseFiles.ANSWERS).readFile());
	}
	
	// Instantiate the class only once
	private static class Initializer {
		static final MessageLoader INSTANCE = new MessageLoader();
	}
	
	public static MessageLoader getInstance() {
		return Initializer.INSTANCE;
	}
		
	/**
	 * Method that returns all clues as a List
	 * @return List of clues
	 */
	public static List<String> getClues() {
		return cluesList;
	}

	/**
	 * Method that populates a List of clues
	 * @param clues List of clues
	 */
	private static final void setClues(List<String> clues) {
		cluesList = clues;
	}

	/**
	 * Method that returns all answers as a List
	 * @return List of answers
	 */
	public static List<String> getAnswers() {
		return answersList;
	}

	/**
	 * Method that populates all answers as a List
	 * @param answers answers as a List
	 */
	private static final void setAnwsers(List<String> answers) {
		answersList = answers;
	}

}
