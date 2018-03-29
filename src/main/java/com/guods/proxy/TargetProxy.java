package com.guods.proxy;

import java.util.List;
/**
 * 该对象初始化Proxy，创建代理的目标对象
 * 可以用jdkProxy实现或cglib实现
 * @author guods
 *
 */
public interface TargetProxy {
	//初始化通知列表，拦截方法pattern
	void init(List<Notify> notifyList, String pattern);
	//创建返回一个代理对象
	public Object getProxyInstance(Object target);
}
