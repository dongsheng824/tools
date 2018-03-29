package com.guods.tools;

import java.util.Date;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsExecutor {

	// java能执行的ScriptEngine
	public void getScriptEngineFactory() {
		ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> factories = manager.getEngineFactories();
		for (ScriptEngineFactory factory : factories) {
			System.out.println(factory.getNames());
		}
	}

	// 直接执行js代码
	public void jsFunction() {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");
		try {
			String script = "var a=3; var b=4;print (a+b);";
			scriptEngine.eval(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 执行带不参数的js代码
	public void jsFunctionWithoutParam() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String script = "function welcom(){return 'welcom '}";
		engine.eval(script);
		Invocable invocable = (Invocable) engine;
		String result = (String) invocable.invokeFunction("welcom");
		System.out.println(result);
	}
	
	// 执行带参数的js代码
	public void jsFunctionWithParam() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		String script = "function welcom(name){return 'welcom ' + name;}";
		engine.eval(script);
		Invocable invocable = (Invocable) engine;
		String result = (String) invocable.invokeFunction("welcom", "guods");
		System.out.println(result);
	}

	// java中如何通过线程来启动一个js方法
	public void testScriptInterface() throws ScriptException {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("js");
		String script = "var obj=new Object();obj.run=function(){println('test thread')}";
		engine.eval(script);
		Object obj = engine.get("obj");// 获取js中对象
		Invocable invocable = (Invocable) engine;
		Runnable r = invocable.getInterface(obj, Runnable.class);
		Thread t = new Thread(r);
		t.start();
	}

	// 将java对象注入到js代码中运行
	public void injectObject() throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Date date = new Date();
		System.out.println("java对象：" + date.getTime());
		engine.put("date", date);
		String script = "function getTime(){return date.getTime();}";
		engine.eval(script);
		Invocable invocable = (Invocable) engine;
		Long result = (Long) invocable.invokeFunction("getTime");
		System.out.println("js执行结果：" + result);
	}

}
