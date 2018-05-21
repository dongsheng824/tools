package com.guods.optimalthreadnumber;

import java.util.concurrent.TimeUnit;

public class TestUtil {

	public static void doWorkByTime(long millisecond) {
		long startTime = System.currentTimeMillis();
		while (true) {
			long endTime = System.currentTimeMillis();
			long costTime = endTime - startTime;
			if (costTime >= millisecond) {
				return;
			}
		}
	}

	public static void doBlockOperation(long millisecond) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(millisecond);
	}
}
