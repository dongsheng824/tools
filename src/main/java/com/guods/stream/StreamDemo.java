package com.guods.stream;

import java.util.stream.Stream;

public class StreamDemo {

	public static void main(String[] args) {
		/*
		 * Math::random 和 ()->Math.random() 相同
		 * 双冒号：这样的是式子并不代表一定会调用这个方法。这种式子一般是用作Lambda表达式。
		 * 
		 * 这个无限长度Stream是懒加载，一般这种无限长度的Stream都会配合Stream的limit()方法来用
		 * 
		 * 转换操作都是lazy的，多个转换操作只会在汇聚操作（见下节）的时候融合起来，一次循环完成。
		 * 我们可以这样简单的理解，Stream里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，
		 * 在汇聚操作的时候循环Stream对应的集合，然后对每个元素执行所有的函数。
		 */
		Stream<Integer> intStream = Stream.of(1, 2, 3, 4);
		//无限长的stream
		Stream<Double> randomStream1 = Stream.generate(()->Math.random());
//		Stream<Double> randomStream2 = Stream.generate(Math::random);
		randomStream1.limit(10).forEach(System.out::println);
		Integer reduce = intStream.reduce(0, (sum, item)->sum + item);
		System.out.println(reduce);
	}
}
