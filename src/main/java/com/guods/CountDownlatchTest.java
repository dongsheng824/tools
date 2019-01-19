package com.guods;

import java.util.concurrent.CountDownLatch;

public class CountDownlatchTest {

	static CountDownLatch countDownLatch = new CountDownLatch(5);
	public static void main(String[] args) throws InterruptedException {
		
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println("countdown:" + Thread.currentThread().getName());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					countDownLatch.countDown();
				}
			}).start();
		}
		
		countDownLatch.await();
		System.out.println("countdown:" + Thread.currentThread().getName());
	}
}
