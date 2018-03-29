package com.guods.proxy;

public class NotifyImpl extends Notify {

	public NotifyImpl(int order) {
		super(order);
	}

	public Object before(Object target) {
		System.out.println("+++++++this is before++++++++++" );
		return null;
	}
	
	public Object after(Object target) {
		System.out.println("++++++++this is after+++++++++++++");
		return null;
	}

	public Object exception(Object target) {
		System.out.println("++++++++this is exception+++++++++++++");
		return null;
	}

	public Object finallyNotify(Object target) {
		System.out.println("++++++++this is finally+++++++++++++");
		return null;
	}

}
