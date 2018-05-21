package com.guods.lockbyredis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * blockingQueueMap，每个lockKey对应一个线程阻塞队列
 * @author guods
 *
 */
public class LockBlockingQueueMap {

	private volatile Map<String, BlockingQueue<Thread>> blockingQueueMap = new HashMap<String, BlockingQueue<Thread>>();
	
	/**
	 * 把锁对应的线程加入到阻塞队列
	 * @param lockKey 锁名
	 * @param thread 线程
	 */
	public synchronized void enQueue(String lockKey, Thread thread) {
		BlockingQueue<Thread> blockingQueue = blockingQueueMap.get(lockKey);
		if (blockingQueue == null) {
			blockingQueue = new LinkedBlockingQueue<Thread>();
			blockingQueueMap.put(lockKey, blockingQueue);
		}
		blockingQueue.add(thread);
	}
	/**
	 * 把锁对应的线程退出队列
	 * @param lockKey
	 * @param thread
	 */
	public synchronized void deQueue(String lockKey, Thread thread) {
		BlockingQueue<Thread> blockingQueue = blockingQueueMap.get(lockKey);
		if (blockingQueue == null) {
			return;
		}
		if (blockingQueue.contains(thread)) {
			blockingQueue.remove(thread);
		}
	}
	
	/**
	 * thread线程是否在lockKey的阻塞队列里
	 * @param lockKey
	 * @param thread
	 * @return
	 */
	public boolean contains(String lockKey, Thread thread) {
		BlockingQueue<Thread> blockingQueue = blockingQueueMap.get(lockKey);
		if (blockingQueue == null) {
			return false;
		}
		if (blockingQueue.contains(thread)) {
			return true;
		}
		return false;
	};
	
	/**
	 * lockKey的阻塞队列大小
	 * @param lockKey
	 * @return
	 */
	public int size(String lockKey) {
		BlockingQueue<Thread> blockingQueue = blockingQueueMap.get(lockKey);
		if (blockingQueue == null) {
			return 0;
		}
		return blockingQueue.size();
	}
	
	/**
	 * 获取lockKey对应的阻塞队列
	 * @param lockKey
	 * @return
	 */
	public BlockingQueue<Thread> getBlockingQueue(String lockKey){
		BlockingQueue<Thread> blockingQueue = blockingQueueMap.get(lockKey);
		if (blockingQueue == null) {
			synchronized (blockingQueueMap) {
				if (blockingQueueMap.get(lockKey) == null) {
					blockingQueue = new LinkedBlockingQueue<Thread>();
					blockingQueueMap.put(lockKey, blockingQueue);
				}
			}
		}
		return blockingQueue;
	}
	/**
	 * 获取lockKey集合
	 * @return
	 */
	public Set<String> getLockKeySet() {
		return blockingQueueMap.keySet();
	}
	
}
