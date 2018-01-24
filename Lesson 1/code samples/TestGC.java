/*
   File: TestGC.java
   Date      Author      Changes
   Aug 14 99 jdb         Created 

   Apr 09 00 jdb         Added timing statements to main()
                         
*/ 

import edu.ucsd.examples.*;

/**
 * This class is a driver for the Rock class.  It's purpose is to
 * use the various methods in the Rock class to demonstrate
 * Java language features.
 * @author J.D. Baker
 * @see Rock
**/
 
public class TestGC 
{
    private static long start;
    private static long stop;
    
    
  public static void main(String args[]) 
  {
     
     System.out.println ("Begin timing.  Run jps to find lmvid, then run visualgc." );
     try
     {
        System.in.read(); 
     }
     catch ( java.io.IOException e )
     { 
        e.printStackTrace();
     }
              
     start =  System.currentTimeMillis();
     for(int i = 0; i < 50000; i++)
     {
        new Rock();
     }
     stop = System.currentTimeMillis();
    
     System.out.println ("Remaining Rocks = " + Rock.getLiveRocks() );
     System.out.println ("loop time = " + (stop - start) );
    
     try
     {
        System.in.read();
     }
     catch ( java.io.IOException e )
     { 
        e.printStackTrace();
     }
          
     start =  System.currentTimeMillis();
     for(int i = 0; i < 50000; i++)
     {
        new Rock();
     }
     stop = System.currentTimeMillis();
    
     System.out.println ("Remaining Rocks = " + Rock.getLiveRocks() );
     System.out.println ("loop time = " + (stop - start) );
    
     try
     {
        System.in.read();
        System.in.read();                
     }
     catch ( java.io.IOException e )
     {  }
  }
}
