package com.guods.lockbyredis;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * redis单机版分布式锁，可重入
 * 
 * @author guods
 *
 */
public class ReentrantRedisLock {
	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_IF_EXIST = "XX";
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
							BlockingQueue<Thread> currentBlockingQueue = lockBlockingQueueMap.getBlockingQueue(lockKey);
							synchronized (currentBlockingQueue) {
								currentBlockingQueue.notify();
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
	public static boolean tryLock(LockEntity lockEntity) {
		try (Jedis jedis = JedisUtil.getJedis()){
			String result = jedis.set(lockEntity.getLockKey(), lockEntity.getLockValue(), 
					SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, lockEntity.getExpireTime());
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
			return false;
		} 
	}

	/**
	 * 加可重入锁
	 * @param lockKey
	 * @param expireTime
	 */
	public static void lock(String lockKey, long expireTime) {
		String requestId = ReentrantRedisLock.getRequestId();
		// 第一次尝试获取锁
		boolean success = tryLock(new LockEntity(lockKey, requestId, expireTime));
		if (success) {
			return;
		}else {
			//检查是否本线程加的锁，如果是本线程加的锁，则重入
			String lockValue = getValue(lockKey);
			if (lockValue != null) {
				try {
					LockEntity lockEntity = LockEntity.buildByKV(lockKey, lockValue);
					if (requestId.equals(lockEntity.getRequestId())) {
						lockEntity.lockIncrease();
						updateSet(lockEntity, expireTime);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			while (!success) {
				// 当前线程放到阻塞队列
				synchronized (lockBlockingQueueMap) {
					if (!lockBlockingQueueMap.contains(lockKey, Thread.currentThread())) {
						lockBlockingQueueMap.enQueue(lockKey, Thread.currentThread());
					}
				}
				// 阻塞当前线程
				BlockingQueue<Thread> currentBlockingQueue = lockBlockingQueueMap.getBlockingQueue(lockKey);
				synchronized (currentBlockingQueue) {
					System.out.println(Thread.currentThread().getName() + "线程进入等待！");
					try {
						currentBlockingQueue.wait();
					} catch (Exception e) {
						System.out.println(Thread.currentThread().getName() + "wait异常！" + e.getMessage()
						+ e.getClass());
						e.printStackTrace();
					}
				}
				// 如果释放锁的时候唤醒线程，则再次获取锁
				success = tryLock(new LockEntity(lockKey, requestId, expireTime));
				System.out.println("二次：" + success);
				if (success) {
					// 如果获取成功，从阻塞队列移除，并返回
					lockBlockingQueueMap.deQueue(lockKey, Thread.currentThread());
					return;
				}
				// 如果获取失败，则继续循环，到阻塞队列等待
			}
			return;
		}
	}
	
	/**
	 * 释放可重入锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public static boolean release(String lockKey, long expireTime) {
		LockEntity lockEntity = null;
		try (Jedis jedis = JedisUtil.getJedis()){
			String requestId = getRequestId();
			try {
				lockEntity = LockEntity.buildByKV(lockKey, jedis.get(lockKey));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (lockEntity == null) {
				return true;
			}
			//如果有本线程的请求，则处理
			if (requestId.equals(lockEntity.getRequestId())) {
				jedis.watch(lockKey);
				try {
					Transaction transaction = jedis.multi();
					//重入次数大于1，则减1，否则删除锁
					lockEntity = lockEntity.lockDecrease();
					if (lockEntity.getState() > 0) {
						updateSet(transaction, lockEntity, expireTime);
					}else {
						transaction.del(lockKey);
					}
					transaction.exec();
				} catch (Exception e) {
					jedis.unwatch();
					return false;
				}
				return true;
			}
			return false;
		} finally {
			if (lockEntity == null || lockEntity.getState() == 0 || lockEntity.getRequestId() == null) {
				notifyBlockedThread(lockKey);
			}
		}
	}
	
	private static String getValue(String lockKey) {
		try (Jedis jedis = JedisUtil.getJedis()){
			return jedis.get(lockKey);
		} 
	}
	
	private static boolean updateSet(LockEntity lockEntity, long expireTime) {
		try (Jedis jedis = JedisUtil.getJedis()){
			String result = jedis.set(lockEntity.getLockKey(), lockEntity.getLockValue(), SET_IF_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
			if (LOCK_SUCCESS.equals(result)) {
				return true;
			}
			return false;
		}
	}
	
	private static boolean updateSet(Transaction transaction, LockEntity lockEntity, long expireTime) {
		transaction.set(lockEntity.getLockKey(), lockEntity.getLockValue());
		transaction.pexpire(lockEntity.getLockKey(), expireTime);
		return true;
	}
	
	private static void notifyBlockedThread(String lockKey) {
		if (lockBlockingQueueMap.contains(lockKey, Thread.currentThread())) {
			lockBlockingQueueMap.deQueue(lockKey, Thread.currentThread());
		}
		if (lockBlockingQueueMap.size(lockKey) > 0) {
			BlockingQueue<Thread> currentBlockingQueue = lockBlockingQueueMap.getBlockingQueue(lockKey);
			synchronized (currentBlockingQueue) {
				currentBlockingQueue.notify();
			}
		}
		System.out.println("阻塞队列大小：" + lockBlockingQueueMap.size(lockKey));
	}

	private static boolean getLocked(String lockKey) {
		try (Jedis jedis = JedisUtil.getJedis()){
			String result = jedis.get(lockKey);
			if (result != null) {
				return true;
			}
			return false;
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
