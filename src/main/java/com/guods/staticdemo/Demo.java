package com.guods.staticdemo;


public class Demo {

	public static void main(String[] args) {
		
		System.out.println(ChildClass.varA);
		System.out.println(ChildClass.varB);
		
		System.out.println(ParentClass.varA);
		System.out.println(ParentClass.varB);
		
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ParentClass.inc();
				}
			}).start();
		}
	}
}
