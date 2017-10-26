package com.guods.tools;

import java.util.HashMap;
import java.util.Map;

import com.guods.tools.encrypt.RSAEncrypt;

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
//    	for (int i = 0; i < 184; i++) {
//    		System.out.println(generator.genRandomCode());
//		}
    	
//    	generator.genRandomPhoneNums("159");
//    	generator.genRandomDates("2017-05-03 08:00:00", "2017-05-04 23:40:00");
//    	generator.genRandomDatePairs("2017-02-01 08:00:00", "2017-02-28 23:00:00", 8L);
//    	ThreadLocalRandom current = ThreadLocalRandom.current();
//    	System.out.println(current.nextDouble());

//    	TextRecognizer textRecognizer = new TextRecognizer();
//    	String result = textRecognizer.recognizeText(new File("c:\\1.bmp"));
//    	System.out.println(new String(result.getBytes("UTF-8")));
    	
//    	ProcessCmd processCmd = new ProcessCmd();
//    	String[] cmd = {"ipconfig", "-all"};
////    	String[] cmd = {"ipconfig"};
//    	String result = processCmd.execCmd(cmd);
//    	System.out.println(result);
    	
//    	Map<Long, String> map = new HashMap<Long, String>();
//    	map.put(12842L, "first");
//    	map.put(new Long(12842), "change");
//    	System.out.println(map.get(12842L));
    	
    	RSAEncrypt.genKeyPair("D:\\");
    }
    
}
