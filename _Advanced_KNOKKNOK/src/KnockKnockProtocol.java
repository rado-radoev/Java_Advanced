
import java.util.Random;
import java.io.*;

/**
 * This class defines the protocol for communication between the Knock Knock Server 
 * and the Knock Knock Client. The class loads in knock knock clues and answers from 
 * the files knockknockclues.txt and knockknockanswers.txt, respectively. It searches 
 * for the files in the directory that the executable jar is from. If it can not find 
 * the files it loads a default set of KnockKnock jokes. This protocol also 
 * randomizes the jokes loaded for each client.
 *  
 * @author Java Tutorials
 * @author Humberto Sainz
 *
 */
public class KnockKnockProtocol 
{
	private JokeState state = JokeState.WAITING;	//default starting state for KKP

    private int NUMJOKES = 5; //default number of jokes changes if file is read incorrectly;
    private int currentJoke = 0;

    private Reader isrClues;
    private BufferedReader readerClues;
    private Reader isrAnswers;
    private BufferedReader readerAnswers;
    private String[] clues;
    private String[] answers;
    private String[] defaultClues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] defaultAnswers = { "Turnip the heat, it's cold in here!",
                                 "I didn't know you could yodel!",
                                 "Bless you!",
                                 "Is there an owl in here?",
                                 "Is there an echo in here?" };

    /**
     * Default constructor that loads in jokes and then randomizes them. 
     */
    public KnockKnockProtocol()
    {
		loadInJokes();
    	randomizeJokes();
    }
    
    /**
     * Method to search load clues and jokes from the files "knockknockclues.txt" and 
     * "knockknockanswers.txt". This method searches for the files mentioned above in 
     * the same directory as the executable jar. If these files can not be found, this
     * method loads in the default jokes provided.
     * 
     */
    private void loadInJokes()
    {
    	// load files from default directory
    	File clueFile = new File("knockknockclues.txt");
    	File answerFile = new File("knockknockanswers.txt");
     
    	// set default jokes if file can not be found;
    	if(!clueFile.exists() || !clueFile.canRead() || 
    			!answerFile.exists() || !answerFile.canRead())
    	{
    		System.out.println("Files for jokes not found. Using default jokes.");
    		clues = defaultClues;
    		answers = defaultAnswers;
    	}
    	else
    	{
    		try
    		{
    			String clue, answer;
    			int clueIndex = getNumberOfLines(clueFile);
    			int answerIndex = getNumberOfLines(answerFile);
       
    			// open streams to attempt to read files
    			isrClues = new InputStreamReader(new FileInputStream(clueFile));
    			readerClues = new BufferedReader(isrClues);
    			isrAnswers = new InputStreamReader(new FileInputStream(answerFile));
    			readerAnswers = new BufferedReader(isrAnswers);

    			// check to make sure there is a clue for every answer (vice versa)
    			if(clueIndex == answerIndex)
    			{
    				NUMJOKES = clueIndex;
    				clues = new String[clueIndex];
    				answers = new String[answerIndex];
    				int count = 0;
        
    				// read files and load array;
    				while((clue = readerClues.readLine()) != null && 
    						(answer = readerAnswers.readLine()) != null)
    				{
    					clues[count] = clue;
    					answers[count] = answer;
    					count++;
    				}
    			}
    			else
    			{
    				System.out.println("# clues does not match # of answers. "
    						+ "Using default jokes.");
    				clues = defaultClues;
    				answers = defaultAnswers;
    			}  
    		}
    		catch(FileNotFoundException e)
    		{
    			System.out.println("Error loading file.");
    			e.printStackTrace();
    			
    			// set default jokes
    			clues = defaultClues;
        		answers = defaultAnswers;
    		}
    		catch(IOException e)
    		{
    			System.out.println("Error loading data.");
    			e.printStackTrace();
    			
    			// set default jokes
    			clues = defaultClues;
        		answers = defaultAnswers;
    		}
    		finally
    		{
    			closeStreams();
    		}
    	}
    }
    
    /**
     * Method to get the number of lines for the associated file parameter. This allows
     * the user to modify the file without having to modify the source code to set the 
     * length of the String arrays that hold the clues and answer.
     *  
     * @param file File to searched for number of lines.  
     * @return int number of lines in the file passed
     */
    private int getNumberOfLines(File file)
    {
    	int lineCount = 0;
    	LineNumberReader lineCounter  = null;
    	
    	try
    	{
    		lineCounter = new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
    		while(lineCounter.readLine() != null); // readThrough all lines;
    		lineCount = lineCounter.getLineNumber();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error with reading # of lines");
    	}
    	finally
    	{
    		try
    		{
    			lineCounter.close();
    		}
    		catch(IOException e)
    		{
    			System.out.println("Error with reading # of lines");
    		}
    	}
    	return lineCount;
     
    }
    
    /**
     * Handles closing the input streams opened for loading in the jokes from the .txt files 
     */
    private void closeStreams()
    {
    	try
    	{
    		if(readerClues != null)
    			readerClues.close();
    		if(isrClues != null)
    			isrClues.close();
    		if(readerAnswers != null)
    			readerAnswers.close();
    		if(isrAnswers != null)
    			isrAnswers.close();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error closing KKP load streams");
    		e.printStackTrace();
    	}
    }
    
    /**
     * This method randomizes the jokes that were loaded in by loadJokes().
     * This method uses a Fisher-Yates algorithm to randomize the clues/jokes.
     */
    private void randomizeJokes()
    {
    	Random random = new Random();
     
    	// assumes that both arrays are of the same length now
    	// shuffles clues according to Fisher-Yates shuffle alg.
    	int randomIndex = 0;
    	String temp = null;
    	for(int i = clues.length - 1; i >= 0; i--)
    	{
    		// get random number from 0 -> i  
    		randomIndex = random.nextInt(i + 1);
      
    		//re-shuffle current index with random number
    		temp = clues[randomIndex];
    		clues[randomIndex] = clues[i];
    		clues[i] = temp;
    		temp = answers[randomIndex];
    		answers[randomIndex] = answers[i];
    		answers[i] = temp;  
    	}
    }
    
    /**
     * Processes the input received from the input stream of the socket connection between 
     * the client and the server to progress throughout the knock knock joke. Outputs some 
     * string based on the state of the joke and whether or not the input was correct for 
     * the state at the time. 
     * 
     * @param theInput string received from input stream
     * @return theOutput string to be sent to output stream
     */
    public String processInput(String theInput) 
    {
    	String theOutput = null;

        if (state == JokeState.WAITING) 
        {
            theOutput = "Knock! Knock!";
            state = JokeState.SENTKNOCK;
        } 
        else if (state == JokeState.SENTKNOCK) 
        {
            if (theInput.equalsIgnoreCase("Who's there?"))
            {
                theOutput = clues[currentJoke];
                state = JokeState.SENTCLUE;
            } 
            else 
            {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                		"Try again. Knock! Knock!";
            }
        } 
        else if (state == JokeState.SENTCLUE) 
        {
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) 
            {
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                state = JokeState.ANOTHER;
            } 
            else 
            {
            	theOutput = "You're supposed to say \"" + 
            			clues[currentJoke] + 
            			" who?\"" + 
            			"! Try again. Knock! Knock!";
                state = JokeState.SENTKNOCK;
            }
        } 
        else if (state == JokeState.ANOTHER) 
        {
            if (theInput.equalsIgnoreCase("y")) 
            {
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = JokeState.SENTKNOCK;
            } 
            else 
            {
                theOutput = "Bye.";
                state = JokeState.UNREACHABLE;
            }
        }
        else if (state == JokeState.UNREACHABLE)
        {
        	theOutput = "";
        }
        return theOutput;
    }
}
