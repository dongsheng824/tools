package com.guods.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolTest threadPoolTest = new ThreadPoolTest();
		ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(4);
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 6, 5L, TimeUnit.SECONDS, 
				workQueue,
				new ThreadFactory() {
					
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName("TestPool-" + thread.getName());
						return thread;
					}
				},
				new RejectedExecutionHandler() {
					
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//						System.out.println(r + "丢弃任务");	
						//以下为重新提交任务
						try {
							System.out.println("1秒后再提交：" + r);
							System.out.println("线程池大小：" + executor.getPoolSize());
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "：再次提交任务：" + r);
						executor.submit(r);
					}
				});
		//
		List<Future<?>> futureList = new ArrayList<Future<?>>();
		for (int i = 0; i < 20; i++) {
//			threadPool.execute(threadPoolTest.buildRunnable(i));
			System.out.println(Thread.currentThread().getName() + "：开始提交任务：" + i);
			futureList.add(threadPool.submit(threadPoolTest.buildCallable(i)));
			System.out.println(Thread.currentThread().getName() + "：任务已提交：" + i);
			System.out.println("阻塞队列长度：" + workQueue.size());
			System.out.println("线程池线程数：" + threadPool.getPoolSize());
		}
		for (Future<?> future : futureList) {
			System.out.println((String)future.get());
		}
		Thread.sleep(40000);
		System.out.println("线程池线程数：" + threadPool.getPoolSize());
//		for (int i = 0; i < 10; i++) {
//			threadPool.execute(threadPoolTest.buildRunnable(i));
//			System.out.println(Thread.currentThread().getName() + "：开始提交任务：" + i);
//			futureList.add(threadPool.submit(threadPoolTest.buildCallable(i)));
//			System.out.println(Thread.currentThread().getName() + "：任务已提交：" + i);
//			System.out.println("阻塞队列长度：" + workQueue.size());
//			System.out.println("线程池线程数：" + threadPool.getPoolSize());
//		}
		threadPool.shutdown();
	}
	
	class MyCallable implements Callable<String>{
		private int i;
		
		public MyCallable(int i) {
			super();
			this.i = i;
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(i * 1000);
			System.out.println(Thread.currentThread().getName() + "执行任务！");
			return "执行完成" + i;
		}
		
	}
	
	class MyRunnable implements Runnable{
		private int i;
		
		public MyRunnable(int i) {
			super();
			this.i = i;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(i * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "执行runnable任务:" + i);
		}
	}
	
	public Runnable buildRunnable(int i) {
		return this.new MyRunnable(i);
	}
	
	public Callable<String> buildCallable(int i){
		return this.new MyCallable(i);
	}
}
