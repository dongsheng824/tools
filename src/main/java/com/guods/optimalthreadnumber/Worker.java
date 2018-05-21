package com.guods.optimalthreadnumber;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable{

	private CountDownLatch countDownLatch;

	public Worker(CountDownLatch countDownLatch) {

		this.countDownLatch = countDownLatch;
	}

	public void run() {

		try {
			while (countDownLatch.getCount() > 0) {

				// do work
				TestUtil.doWorkByTime(Constants.getRandom());

				// do block operation
				TestUtil.doBlockOperation(Constants.getRandom());

				countDownLatch.countDown();
			}
		} catch (InterruptedException e) {
		}

	}
}
