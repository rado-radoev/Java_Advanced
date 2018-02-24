package com.knockknock.protocol;

import java.security.SecureRandom;
import java.util.List;

import com.knockknock.message.reader.MessageLoader;

public class KnockKnockProtocol {

    private State state = State.WAITING;
    
    
    private final List<String> clues = MessageLoader.getClues();
    private final List<String> answers = MessageLoader.getAnswers();
    
    private int currentJoke = new SecureRandom().nextInt(clues.size());

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
                    
                    int newJoke = new SecureRandom().nextInt(clues.size());
                 
                    if (currentJoke == newJoke)
                        currentJoke = new SecureRandom().nextInt(clues.size());
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
