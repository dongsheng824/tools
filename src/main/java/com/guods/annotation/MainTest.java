package com.guods.annotation;

import java.text.ParseException;

public class MainTest {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, ParseException {
		TestEntity entity = new TestEntity();
		System.out.println(entity);
		entity = AnnotationHandler.handle(entity);
		System.out.println(entity);
		
	}
}
