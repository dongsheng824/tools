package com.guods.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

	Object target;
	Notify notify;

	public MyInvocationHandler(Object target, Notify notify) {
		super();
		this.target = target;
		this.notify = notify;
	}

	public TestModel getProxyInstance(){
		TestModel newProxyInstance = (TestModel)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{TestModel.class}, this);
		return newProxyInstance;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result;
		if (method.getName().contains("Test")) {
			try {
				notify.before(target);
				result = method.invoke(target, args);
				notify.after(target);
			} catch (Exception e) {
				notify.except(target);
				return null;
			} finally {
				notify.finallyNotify(target);
			}
		}else {
			result = method.invoke(target, args);
		}
		return result;
	}
}
