package edu.ucsd.examples;

/*
   File: Rock.java
   Date      Author      Changes
   Aug 14 99 jdb         Created from Eckel's example 
   Aug 20 99 jdb         Commented out println() statements in the
                         constructor and the finalize() method.  Also
                         commented out the read() that paused execution
                         of the finalize().
   Apr 09 00 jdb         Formatting changes - corrected indentation
                         misalignments of documentation comments and
                         some code structure in constructor.
*/ 


  /**
   * A simple class to demonstrate several Java features.
   * This class demonstrates static and instance variables
   * and methods, along with accessor methods, and
   * the finalize method.  This class grew out of a class
   * provided by Bruce Eckel in his "Thinking in Java" book.
   * It is public so that we can demonstrate javadoc features.
   * The expected output of a program like TestGarbageCollection
   * that exercises this class 50,000 times looks like:
   * <pre>
   * Death of a Rock #46502
   * Creating Rock #49992
   * Creating Rock #49993
   * Death of a Rock #46503
   * Creating Rock #49994
   * Creating Rock #49995
   * Creating Rock #49996
   * Creating Rock #49997
   * Creating Rock #49998
   * Creating Rock #49999
   * Creating Rock #50000
   * Remaining Rocks = 3498
   *
   * </pre>
   * Note that the number of Remaining Rocks will change
   * based on a number of factors, including other
   * programs running, size of memory, etc.
   * @author J.D. Baker
   * @author derived from Bruce Eckel's Rock class
   * @see Object
   * @see TestGarbageCollection 
   *  
  **/  
 

public class Rock
{

   private static int totalRocks;   // tracks the total # of Rocks created
   private static int liveRocks;    // tracks the number of Rocks currently "alive"
   private int thisRock;            // the instance number of a Rock object
   
  /**
   *  the Rock class constructor 
  **/
  public Rock() 
  { 
    totalRocks++;    
    liveRocks++;
    //this slows the execution down considerably 
    System.out.println("Creating Rock #" + totalRocks );
    
    thisRock = totalRocks;   
  }

  
  /**
   *  the Rock class finalize method
   *  allows the tracking of Rock objects that have
   *  not been garbage collected
   * @exception Throwable  The grandaddy of all exceptions, from
   *  the definition in Object  
  **/  
  protected void finalize ()
  throws java.lang.Throwable
  {
     int dummy;  // lvalue for read
     
     //System.out.println ("Death of a Rock #" + thisRock );
     liveRocks--;
    
     //dummy = System.in.read();
  }

  /**
   *  accessor method to determine the number of Rocks
   *  that have not been garbage collected
   * @return liveRocks 
   **/  
  public static int getLiveRocks ()
  {
     return liveRocks;
  }
  
  /**
   *  accessor method to determine the number of Rocks
   *  that have been created
   * @return totalRocks 
  **/  
  public static int getTotalRocks ()
  {
     return totalRocks;
  }  
}

/* two runs without the System.out.println()

V:\My Documents\UCSD\Java\On-line Java IV\Lesson 1\code samples>java TestGC
Begin timing...
Remaining Rocks = 47927
loop time = 47

Remaining Rocks = 93846
loop time = 79


V:\My Documents\UCSD\Java\On-line Java IV\Lesson 1\code samples>java TestGC
Begin timing...
Remaining Rocks = 47877
loop time = 31

Remaining Rocks = 46957
loop time = 187

*/


