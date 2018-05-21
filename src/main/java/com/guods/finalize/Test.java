package com.guods.finalize;

public class Test {

	public void print() {
		System.out.println("print。。。。");
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("执行finalize");
		System.out.println("finalize thread:" + Thread.currentThread().getName());
		super.finalize();
	}
	
}
