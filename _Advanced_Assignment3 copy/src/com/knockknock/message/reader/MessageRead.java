package com.knockknock.message.reader;

import java.util.ArrayList;
import java.util.List;

public class MessageRead {

	public static void main(String[] args) {
		MessageReader reader = new MessageReader(ResponseFiles.CLUES);
		List<String> strings = reader.readFile();
		System.out.println(strings); 
	}

}
