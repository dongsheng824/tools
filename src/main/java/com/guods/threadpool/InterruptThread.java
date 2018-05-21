package com.guods.threadpool;

public class InterruptThread {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("thread begin sleep");
					Thread.sleep(1000000);
					System.out.println("thread end sleep");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		thread.interrupt();
		Thread.sleep(2000);
		System.out.println("exit");
	}
}
