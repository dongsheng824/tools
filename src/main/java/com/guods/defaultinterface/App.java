package com.guods.defaultinterface;

public class App {

	public static void main(String[] args) {
		Defaultable defaultTest = new DefaultTestImpl();
		System.out.println(defaultTest.defaultTest("Test"));
		Defaultable overrideTest = new OverrideTestImpl();
		System.out.println(overrideTest.defaultTest("Test"));
	}
}
