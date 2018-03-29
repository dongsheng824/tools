package com.guods.reflect;

import com.guods.modle.User;
import com.guods.tools.ReflectUtil;

public class ReflectTest {

	public static void main(String[] args) {
		User user = new User("jack", "13800000001");
		Object invokeMethod = ReflectUtil.invokeMethod(user, "print");
		System.out.println(invokeMethod);
//		ReflectUtil.printMethods(user);
	}
}
