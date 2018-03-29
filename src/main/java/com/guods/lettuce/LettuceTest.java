package com.guods.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceTest {

	public static void main(String[] args) {
		// syncTest();
		asyncTest();
	}

	/**
	 * 同步模式
	 */
	public static void syncTest() {
		// 创建客户端
		RedisClient redisClient = RedisClient.create("redis://localhost:6379/1");
		// 创建连接
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		// 获取同步指令集
		RedisCommands<String, String> commands = connect.sync();
		// 操作数据
		commands.set("hello", "小明");
		System.out.println("=============sync：" + commands.get("hello"));
		// 关闭连接、客户端
		connect.close();
		redisClient.shutdown();
	}

	/**
	 * 异步模式
	 */
	public static void asyncTest() {
		// 创建客户端
		RedisClient redisClient = RedisClient.create("redis://localhost:6379/1");
		// 创建连接
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		// 获取同步指令集
		RedisAsyncCommands<String, String> commands = connect.async();
		// 操作数据
		RedisFuture<String> future = commands.set("async", "小王");
		future.whenComplete((a, b) -> System.out.println(a + ":" + b));
		
		future = commands.get("async");
		future.whenComplete((a, b) -> System.out.println(a + ":" + b));
		// 等2秒，保证操作结果已返回。
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 关闭连接、客户端
		connect.close();
		redisClient.shutdown();
	}
}
