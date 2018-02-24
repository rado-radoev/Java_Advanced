package com.knockknock.message.reader;

import java.util.List;

public class MessageLoader {
	
	private static List<String> cluesList;
	private static List<String> answersList;
	
	private MessageLoader() { 
		if (cluesList == null)
			setClues(new MessageReader(ResponseFiles.CLUES).readFile());
		if (answersList == null)
			setAnwsers(new MessageReader(ResponseFiles.ANSWERS).readFile());
	}
	
	private static class Initializer {
		static final MessageLoader INSTANCE = new MessageLoader();
	}
	
	public static MessageLoader getInstance() {
		return Initializer.INSTANCE;
	}
		

	public static List<String> getClues() {
		return cluesList;
	}

	private static final void setClues(List<String> clues) {
		cluesList = clues;
	}

	public static List<String> getAnswers() {
		return answersList;
	}

	private static final void setAnwsers(List<String> ansers) {
		answersList = ansers;
	}

}
