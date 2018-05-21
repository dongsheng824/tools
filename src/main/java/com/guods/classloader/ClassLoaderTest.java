package com.guods.classloader;

public class ClassLoaderTest {

	public static void main(String[] args) throws Exception {
		MyClassLoader myClassLoader = new MyClassLoader();
		System.out.println(myClassLoader);
		Class<?> clazz = Class.forName("com.guods.modle.User", true, myClassLoader);
		Object obj = clazz.newInstance();
		System.out.println("User的类加载器:" + clazz.getClassLoader());
		System.out.println("上下文类加载器:" +Thread.currentThread().getContextClassLoader());
	}
}
