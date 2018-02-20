package com.knockknock.protocol;

import java.security.SecureRandom;
import java.util.ArrayList;

import com.knockknock.message.reader.MessageReader;
import com.knockknock.message.reader.ResponseFiles;

public class KnockKnockProtocol {

    private State state = State.WAITING;
    
    private final ArrayList<String> clues = new MessageReader(ResponseFiles.CLUES).textToStringArray();
    private final ArrayList<String> answers = new MessageReader(ResponseFiles.ANSWERS).textToStringArray();
    private final String[] clues1 = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private final String[] answers1 = { "Turnip the heat, it's cold in here!",
                                 "I didn't know you could yodel!",
                                 "Bless you!",
                                 "Is there an owl in here?",
                                 "Is there an echo in here?" };
    
    private int currentJoke = new SecureRandom().nextInt(clues.size() + 1);

    public String processInput(String theInput) {
        String theOutput = null;

        switch (state) {
            case WAITING:
                theOutput = "Knock! Knock!";
                state = State.SENTKNOCKKNOCK;
                break;
            case SENTKNOCKKNOCK:
                if (theInput.equalsIgnoreCase("Who's there?")) {
                    theOutput = clues.get(currentJoke);
                    state = State.SENTCLUE;
                } else {
                    theOutput = "You're supposed to say \"Who's there?\"! " +
                            "Try again. Knock! Knock!";
                }   
                break;
            case SENTCLUE:
                if (theInput.equalsIgnoreCase(clues.get(currentJoke) + " who?")) {
                    theOutput = answers.get(currentJoke) + " Want another? (y/n)";
                    state = State.ANOTHER;
                } else {
                    theOutput = "You're supposed to say \"" +
                            clues.get(currentJoke) +
                            " who?\"" +
                            "! Try again. Knock! Knock!";
                }   
                break;
            case ANOTHER:
                if (theInput.equalsIgnoreCase("y")) {
                    theOutput = "Knock! Knock!";
                    
                    int newJoke = new SecureRandom().nextInt(clues.size() + 1);
                 
                    if (currentJoke == newJoke)
                        currentJoke = new SecureRandom().nextInt(clues.size() + 1);
                    else
                        currentJoke = newJoke;
                    state = State.SENTKNOCKKNOCK;
                } else {
                    theOutput = "Bye.";
                    state = State.WAITING;
                }   break;
            default:
                break;
        }
        return theOutput;
    }
}
