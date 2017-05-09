package com.guods.tools;

import java.text.ParseException;
import java.util.concurrent.ThreadLocalRandom;

import javax.script.ScriptException;

import com.guods.tools.execjs.JsExecutor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException, ScriptException, NoSuchMethodException
    {
    	JsExecutor jsExecutor = new JsExecutor();
    	jsExecutor.getScriptEngineFactory();
    	jsExecutor.jsFunction();
    	jsExecutor.testScriptInterface();
    	jsExecutor.injectObject();
    	jsExecutor.jsFunctionWithParam();
    	jsExecutor.jsFunctionWithoutParam();
    	
//    	RandomGenerator generator = new RandomGenerator();
//    	generator.genRandomPhoneNums("159");
//    	generator.genRandomDates("2017-05-03 08:00:00", "2017-05-04 23:40:00");
//    	generator.genRandomDatePairs("2017-02-01 08:00:00", "2017-02-28 23:00:00", 8L);
//    	ThreadLocalRandom current = ThreadLocalRandom.current();
//    	System.out.println(current.nextDouble());

    }
    
}
