package com.knockknock.message.reader;

import java.util.ArrayList;

public class MessageRead {

	public static void main(String[] args) {
		MessageReader reader = new MessageReader();
		ArrayList<String> strings = reader.textToStringArray();
		System.out.println(strings.get(1)); 
	}

}
