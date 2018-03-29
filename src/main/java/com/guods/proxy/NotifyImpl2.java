package com.guods.proxy;

public class NotifyImpl2 extends Notify {

	public NotifyImpl2(int order) {
		super(order);
	}

	public Object before(Object target) {
		System.out.println("+++++++this is before2++++++++++" );
		return null;
	}
	
	public Object after(Object target) {
		System.out.println("++++++++this is after2+++++++++++++");
		return null;
	}

	public Object exception(Object target) {
		System.out.println("++++++++this is exception2+++++++++++++");
		return null;
	}

	public Object finallyNotify(Object target) {
		System.out.println("++++++++this is finally2+++++++++++++");
		return null;
	}

}
