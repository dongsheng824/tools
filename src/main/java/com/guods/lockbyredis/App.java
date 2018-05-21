package com.guods.lockbyredis;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class App {

	private static String lockKey = "lock04";
	private static int expireTime = 2000;
	private static int count = 0;
	private static CountDownLatch countDownLatch = new CountDownLatch(1000);
	
	public static void main(String[] args) throws InterruptedException {
		long time0 = new Date().getTime();
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					doTranctionByQueue();
//					doTranctionRandomRetry();
					countDownLatch.countDown();
				}
			}).start();
		}
		countDownLatch.await();
		System.out.println(lockKey + "：" + count + " time used:" + (new Date().getTime() - time0));
	}

	public static void doTranctionRandomRetry() {
		String requestId = UUID.randomUUID().toString();
		boolean locked = false;
		long time0 = 0;
		while (!locked) {
			locked = RedisLock.tryLock(lockKey, requestId, expireTime);
			if (!locked) {
				//如果没获取到，则等待一个随机时间后再重新获取
				ThreadLocalRandom random = ThreadLocalRandom.current();
				int randomInt = random.nextInt()%100;
				try {
					Thread.sleep(Math.abs(randomInt));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(locked);
		//处理
		count++;
		System.out.println(Thread.currentThread().getName() + "：after：" + count);
		//释放锁
		time0 = new Date().getTime();
		RedisLock.release(lockKey);
		System.out.println("释放锁时间：" + (new Date().getTime() - time0));
	}
	
	public static void doTranctionByQueue() {
		//上锁
//		ReentrantRedisLock.lock(lockKey, expireTime);
		ReentrantRedisLock.lock(lockKey, expireTime);
//		RedisLock.lock(lockKey, expireTime);
		//处理
		count++;
		System.out.println(Thread.currentThread().getName() + "：after：" + count);
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//释放锁
//		ReentrantRedisLock.release(lockKey, expireTime);
		ReentrantRedisLock.release(lockKey, expireTime);
//		RedisLock.release(lockKey);
	}
}
