package com.guods.proxy;

import java.util.List;

/**
 * 代理对象生产工厂
 * @author guods
 *
 */
public class ProxyFactory {
	
	private String type;
	private List<Notify> notifyList;
	private String pattern;
	private TargetProxy targetProxy;
	private NotifyComparator notifyComparator = new NotifyComparator();

	public ProxyFactory configure() {
		switch (type) {
		case "jdkproxy":
			targetProxy = new JDKProxy(notifyList, pattern);
			break;
		case "cglib":
			targetProxy =  new CGlibProxy(notifyList, pattern);
			break;
		}
		return this;
	}
	
	public <T> T bulidProxyObject(T target) {
		@SuppressWarnings("unchecked")
		T proxyInstance = (T)targetProxy.getProxyInstance(target);
		return proxyInstance;
	}

	public ProxyFactory() {
		super();
	}

	public ProxyFactory(String type, List<Notify> notifyList, String pattern) {
		super();
		this.type = type;
		//按自定义的Notify.order排序，order值小的切面方法先执行
		notifyList.sort(notifyComparator);
		this.notifyList = notifyList;
		this.pattern = pattern;
	}

	public String getType() {
		return type;
	}

	public ProxyFactory setType(String type) {
		this.type = type;
		return this;
	}

	public List<Notify> getNotifyList() {
		return notifyList;
	}

	public ProxyFactory setNotifyList(List<Notify> notifyList) {
		notifyList.sort(notifyComparator);
		this.notifyList = notifyList;
		return this;
	}

	public String getPattern() {
		return pattern;
	}

	public ProxyFactory setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}
	
}
