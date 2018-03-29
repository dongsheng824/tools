package com.guods.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGlibProxy implements MethodInterceptor, TargetProxy{

	private Object target;
	private List<Notify> notifyList;
	private NotifyHandler notifyHandler= new NotifyHandler();
	private String pattern;

	public CGlibProxy(List<Notify> notifyList, String pattern) {
		super();
		init(notifyList, pattern);
	}

	public void init(List<Notify> notifyList, String pattern) {
		this.notifyList = notifyList;
		this.pattern = pattern;
	}
	
	public Object getProxyInstance(Object object) {
		this.target = object;
		return Enhancer.create(object.getClass(), this);
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object returnObj = null;
		//拦截pattern匹配的方法
		if (method.getName().matches(pattern)) {
			System.out.println("this is cglib");
			try {
				notifyHandler.handleBeforeMethods(notifyList, target);
				returnObj = method.invoke(target, args);
				notifyHandler.handleAfterMethods(notifyList, target);
			} catch (Exception e) {
				notifyHandler.handleExceptionMethods(notifyList, target);
			} finally {
				notifyHandler.handleFinalMethods(notifyList, target);
			}
		}else {
			returnObj = proxy.invoke(target, args);
		}
		return returnObj;
	}
}
