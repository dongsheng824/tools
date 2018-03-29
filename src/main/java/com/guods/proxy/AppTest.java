package com.guods.proxy;

import java.util.ArrayList;
import java.util.List;

public class AppTest {
    
	public static void main(String[] args) {
		TestModel testModel = new TestModelImpl("aaa", 10);
		TestModel proxyInstance;
		//设置aop通知列表
		List<Notify> notifyList = new ArrayList<Notify>();
		notifyList.add(new NotifyImpl(2));
		notifyList.add(new NotifyImpl2(3));
		//创建代理工厂，设置拦截方法，设置通知列表
		ProxyFactory proxyFactory = new ProxyFactory()
				.setPattern("print.*").setNotifyList(notifyList);
		//使用jdk代理实现。被代理类必须有接口
		proxyFactory.setType("jdkproxy").configure();
		System.out.println("--------------------------------------------------");
		proxyInstance = proxyFactory.bulidProxyObject(testModel);
		proxyInstance.printTest();
		System.out.println(proxyInstance);
		System.out.println();
		proxyInstance.normalMethod();
		//使用cglib代理实现
		proxyFactory.setType("cglib").configure();
		System.out.println("--------------------------------------------------");
		proxyInstance = proxyFactory.bulidProxyObject(testModel);
		proxyInstance.printTest();
		System.out.println(proxyInstance);
		System.out.println();
		proxyInstance.normalMethod();
	}
}
