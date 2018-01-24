package edu.ucsd.hello;
/**
 * Write a description of class Mouth here.
 * 
 * @author J.D. Baker modified from a version published by Shengyang Shong 
 * @version 02/02/2002
 */
public class Mouth
{
    // instance variables - replace the example below with your own
    private final String mumble;

    /**
     * Default Constructor for objects of class Mouth
     */
    public Mouth()
    {
        // initialise instance variables
        mumble = "ah.. er.. ah.. oh..";
    }

    /**
     *  Constructor for objects of class Mouth that
     *  creates a user-defined mumble
     */
    public Mouth(String mumble)
    {
        // initialise instance variables
        this.mumble = mumble;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
    **/
    public void say(String what)
    {
        // put your code here
        System.out.println(what);
    }

    /**
     * The default say what method that just mumbles.
     * Note that this is an overloaded instance method.
    **/
    public void say()
    {
        // put your code here
        System.out.println(mumble);
    }
}
