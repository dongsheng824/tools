package com.guods.tools;

import java.io.File;
import java.text.ParseException;

import javax.script.ScriptException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
//    	JsExecutor jsExecutor = new JsExecutor();
//    	jsExecutor.getScriptEngineFactory();
//    	jsExecutor.jsFunction();
//    	jsExecutor.testScriptInterface();
//    	jsExecutor.injectObject();
//    	jsExecutor.jsFunctionWithParam();
//    	jsExecutor.jsFunctionWithoutParam();
    	
//    	RandomGenerator generator = new RandomGenerator();
//    	generator.genRandomPhoneNums("159");
//    	generator.genRandomDates("2017-05-03 08:00:00", "2017-05-04 23:40:00");
//    	generator.genRandomDatePairs("2017-02-01 08:00:00", "2017-02-28 23:00:00", 8L);
//    	ThreadLocalRandom current = ThreadLocalRandom.current();
//    	System.out.println(current.nextDouble());

    	TextRecognizer textRecognizer = new TextRecognizer();
    	String result = textRecognizer.recognizeText(new File("c:\\1.bmp"));
    	System.out.println(new String(result.getBytes("UTF-8")));
    }
    
}
