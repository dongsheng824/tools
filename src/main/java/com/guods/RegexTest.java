package com.guods;

public class RegexTest {

	public static void main(String[] args) {
		String target = "hello world";
		boolean matches = target.matches(".*world");
		System.out.println(matches);
		
	}
}
