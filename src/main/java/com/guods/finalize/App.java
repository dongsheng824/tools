package com.guods.finalize;


public class App {

	public static void main(String[] args) throws InterruptedException {
		new Test();
		System.gc();//GC的时候才会执行回收对象的finalize()
		System.out.println("main thread:" + Thread.currentThread().getName());
//		Thread.sleep(4000);
		System.out.println("end！");
	}
}
