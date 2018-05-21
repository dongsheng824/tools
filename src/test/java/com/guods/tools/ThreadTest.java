package com.guods.tools;

public class ThreadTest {

	public static void main(String[] args) throws InterruptedException {
		Object obj = new Object();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (obj) {
						System.out.println("before wait");
						obj.wait();
						Thread.sleep(1000000);
						System.out.println("after wait");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		Thread.sleep(2000);
		thread.interrupt();
		System.out.println("thread interrupted:" + thread.getName());
	}
}
