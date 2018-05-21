package com.guods.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	private static Integer count = 0;
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					lock.lock();
					lock.lock();
					lock.lock();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					System.out.println(Thread.currentThread().getName() + ":" + count);
					count++;
					lock.unlock();
					lock.unlock();
					lock.unlock();
				}
			}).start();
		}
	}
}
