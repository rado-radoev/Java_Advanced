package com.knockknock.protocol;

import java.security.SecureRandom;
import java.util.List;

import com.knockknock.message.reader.MessageLoader;

/**
* <h1>Knock Knock Protocol</h1>
* Implements the protocol that the client and server use to communicate. 
* This class keeps track of where the client and the server are in their conversation
* and serves up the server's response to the client's statements. 
* The KnockKnockProtocol object contains the text of all the jokes and makes sure that
* the client gives the proper response to the server's statements.
* 
* <p>Class randomizes jokes for each client
*
* @author  Radoslav Radoev
* @version %I%, %G%
* @since   02/25/2018
*/
public class KnockKnockProtocol {

    private State state = State.WAITING;
    
    private final List<String> clues = MessageLoader.getClues();
    private final List<String> answers = MessageLoader.getAnswers();
    
    private int currentJoke = new SecureRandom().nextInt(clues.size());

    /**
     * Method to control communication between client and server.
     * Depending on client's question appropriate answer is given
     * 
     * <p> Communication will not be moved forward unless correct answer is 
     * provided from client
     * 
     * <p> Communication is closed when server displays "Bye"
     * 
     * @param theInput the user's input
     * @return server's answer to users question as a String
     */
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
