package com.guods.tools;

import java.text.ParseException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException
    {
    	RandomGenerator generator = new RandomGenerator();
    	
//    	generator.genPhoneNums("169");
    	
//    	generator.genRandomDates("2017-02-01 08:00:00", "2017-02-28 23:00:00");
    	
    	generator.genRandomDatePairs("2017-02-01 08:00:00", "2017-02-28 23:00:00", 8L);
    	
    }
    
}
