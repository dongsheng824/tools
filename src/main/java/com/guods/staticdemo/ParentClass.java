package com.guods.staticdemo;

public class ParentClass {

	public static String varA = "this is parent varA";
	public static final String varB = "this is parent var B";
	public static int a = 0;
	
	public synchronized static void inc() {
		a = a + 1;
		System.out.println(Thread.currentThread().getName() + ":" + a);
		System.out.println(Thread.currentThread().holdsLock(ParentClass.class));
	}
}
