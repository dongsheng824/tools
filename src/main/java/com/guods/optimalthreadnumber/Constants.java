package com.guods.optimalthreadnumber;

import java.util.concurrent.ThreadLocalRandom;

public class Constants {

	public static final int SIZE = 100;

	public static final int THREAD_SIZE = 8;

	public static long BLOCK_TIME_MILLISECOND = 5L;

	public static long WORK_TIME_MILLISECOND = 5L;
	
	public static long getRandom() {
		long i = ThreadLocalRandom.current().nextLong()%10;
		if (i < 0) {
			return -i;
		}
		return i;
	}
}
