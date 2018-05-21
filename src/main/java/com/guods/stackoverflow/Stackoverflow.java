package com.guods.stackoverflow;

public class Stackoverflow {

	private static int count = 0;

	public static void main(String[] args) {
		test();
	}

	public static void addCount() {
		long a = 100000;
		long b = 100000;
		long c = 100000;
		count++;
		addCount();
	}

	public static void test() {
		try {
			addCount();
		} catch (Throwable e) {
			System.out.println(e);
			System.out.println("stack depth:" + count);
		}
	}
}
