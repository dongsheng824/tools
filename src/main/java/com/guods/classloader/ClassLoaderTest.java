package com.guods.classloader;

public class ClassLoaderTest {

	public static void main(String[] args) throws Exception {
		MyClassLoader myClassLoader = new MyClassLoader();
		Class<?> clazz = Class.forName("TestModel", true, myClassLoader);
		Object obj = clazz.newInstance();
		System.out.println("testModel的类加载器:" + obj.getClass().getClassLoader());
		System.out.println("上下文类加载器:" +Thread.currentThread().getContextClassLoader());
	}
}
