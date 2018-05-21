package com.guods.lockbyredis;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * redis单机版分布式锁
 * 
 * @author guods
 *
 */
public class RedisLock {
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";

	private static LockBlockingQueueMap lockBlockingQueueMap = new LockBlockingQueueMap();
	private static final ThreadLocal<String> requestThreadLocal = new ThreadLocal<String>();
	
	static {
		//启动守护线程，如果阻塞队列里有，并且redis里锁没占用，则唤醒一个线程
		new ScheduledThreadPoolExecutor(1).schedule(new Runnable() {
			@Override
			public void run() {
				Set<String> lockKeySet;
				while (true) {
					lockKeySet = lockBlockingQueueMap.getLockKeySet();
					for (String lockKey : lockKeySet) {
						//遍历每一把锁，把锁对应的阻塞队列不空，并且redis上没有加锁的队列，唤醒一个阻塞线程
						if (lockBlockingQueueMap.size(lockKey) > 0 && !getLocked(lockKey)) {
							synchronized (lockBlockingQueueMap.getBlockingQueue(lockKey)) {
								lockBlockingQueueMap.getBlockingQueue(lockKey).notify();
							}
						}
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, 20, TimeUnit.MILLISECONDS);
	}
	/**
	 * 单次不可重入锁，没有阻塞队列
	 * 
	 * 尝试获取分布式锁。 首先，set()加入了NX参数，可以保证如果已有key存在，则函数不会调用成功，也就是只有一个客户端能持有锁，满足互斥性。
	 * 其次，由于我们对锁设置了过期时间，即使锁的持有者后续发生崩溃而没有解锁，锁也会因为到了过期时间而自动解锁（即key被删除），不会发生死锁。
	 * 最后，因为我们将value赋值为requestId，代表加锁的客户端请求标识，那么在客户端在解锁的时候就可以进行校验是否是同一个客户端。
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @param expireTime
	 *            超期时间
	 * @return 是否获取成功
	 */
	public static boolean tryLock(String lockKey, String requestId, int expireTime) {
		Jedis jedis = JedisUtil.getJedis();
		try {
			String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
			return false;
		} finally {
			jedis.close();
		}
	}

	/**
	 * 加不可重入锁
	 * @param lockKey
	 * @param expireTime
	 */
	public static void lock(String lockKey, int expireTime) {
		String requestId = RedisLock.getRequestId();
		// 第一次获取锁
		boolean success = tryLock(lockKey, requestId, expireTime);
		// 如果没取到
		while (!success) {
			// 当前线程放到阻塞队列
			if (!lockBlockingQueueMap.contains(lockKey, Thread.currentThread())) {
				lockBlockingQueueMap.enQueue(lockKey, Thread.currentThread());
			}
			try {
				// 阻塞当前线程
				synchronized (lockBlockingQueueMap.getBlockingQueue(lockKey)) {
					lockBlockingQueueMap.getBlockingQueue(lockKey).wait();
				}
				// 如果释放锁的时候唤醒线程，则再次获取锁
				success = tryLock(lockKey, requestId, expireTime);
				System.out.println("二次：" + success);
				if (success) {
					// 如果获取成功，从阻塞队列移除，并返回
					lockBlockingQueueMap.deQueue(lockKey, Thread.currentThread());
					return;
				}
				// 如果获取失败，则继续循环，到阻塞队列等待
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	/**
	 * 释放不可重入锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public static boolean release(String lockKey) {
		Jedis jedis = JedisUtil.getJedis();
		try {
			String requestId = RedisLock.getRequestId();
			jedis.watch(lockKey);
			if (requestId.equals(jedis.get(lockKey))) {
				Transaction transaction = jedis.multi();
				transaction.del(lockKey);
				transaction.exec();
				return true;
			}
			return false;
		} finally {
			notifyBlockedThread(lockKey);
			jedis.close();
		}
	}
	
	private static void notifyBlockedThread(String lockKey) {
		if (lockBlockingQueueMap.contains(lockKey, Thread.currentThread())) {
			lockBlockingQueueMap.deQueue(lockKey, Thread.currentThread());
		}
		if (lockBlockingQueueMap.size(lockKey) > 0) {
			synchronized (lockBlockingQueueMap.getBlockingQueue(lockKey)) {
				lockBlockingQueueMap.getBlockingQueue(lockKey).notify();
			}
		}
		System.out.println("阻塞队列大小：" + lockBlockingQueueMap.size(lockKey));
	}

	private static boolean getLocked(String lockKey) {
		Jedis jedis = JedisUtil.getJedis();
		try {
			String result = jedis.get(lockKey);
			if (result != null) {
				return true;
			}
			return false;
		} finally {
			jedis.close();
		}
	}
	
	private static String getRequestId() {
		String requestId = requestThreadLocal.get();
		if (requestId == null) {
			requestId = UUID.randomUUID().toString();
			requestThreadLocal.set(requestId);
		}
		return requestId;
	}
	
}
