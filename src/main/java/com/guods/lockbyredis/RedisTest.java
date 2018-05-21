package com.guods.lockbyredis;

import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;

public class RedisTest {

	private static AtomicInteger count = new AtomicInteger(0);
	
	public static void main(String[] args) {
		for (int i = 0; i < 5000; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try(Jedis jedis = JedisUtil.getJedis();){
						count.incrementAndGet();
						jedis.set("key", count.toString());
						System.out.println(count.toString());
					}
					
				}
			}).start();
		}
	}
}
