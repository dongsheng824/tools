package com.guods.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JDKProxy implements InvocationHandler, TargetProxy {

	private Object target;
	private List<Notify> notifyList;
	private NotifyHandler notifyHandler= new NotifyHandler();
	private String pattern;

	public JDKProxy(List<Notify> notifyList, String pattern) {
		super();
		init(notifyList, pattern);
	}

	public void init(List<Notify> notifyList, String pattern) {
		this.notifyList = notifyList;
		this.pattern = pattern;
	}
	
	public Object getProxyInstance(Object target){
		this.target = target;
		Object newProxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{TestModel.class}, this);
		return newProxyInstance;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result;
		if (method.getName().matches(pattern)) {
			System.out.println("this is jdkproxy");
			try {
				notifyHandler.handleBeforeMethods(notifyList, target);
				result = method.invoke(target, args);
				notifyHandler.handleAfterMethods(notifyList, target);
			} catch (Exception e) {
				notifyHandler.handleExceptionMethods(notifyList, target);
				return null;
			} finally {
				notifyHandler.handleFinalMethods(notifyList, target);
			}
		}else {
			result = method.invoke(target, args);
		}
		return result;
	}

}
