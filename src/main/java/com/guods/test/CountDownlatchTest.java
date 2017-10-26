package com.guods.test;

import java.util.concurrent.CountDownLatch;

public class CountDownlatchTest {

	static CountDownLatch countDownLatch = new CountDownLatch(2);
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
				System.out.println("countdown:" + Thread.currentThread().getName());
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				countDownLatch.countDown();
				System.out.println("countdown:" + Thread.currentThread().getName());
			}
		}).start();
		countDownLatch.await();
		System.out.println("countdown:" + Thread.currentThread().getName());
	}
}
