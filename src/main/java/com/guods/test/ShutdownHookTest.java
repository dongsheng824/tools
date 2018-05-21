package com.guods.test;

public class ShutdownHookTest {

	
	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ":main");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + ":ShutdownHook");
				
			}
		}));
		Thread.sleep(2000);
	}
}
