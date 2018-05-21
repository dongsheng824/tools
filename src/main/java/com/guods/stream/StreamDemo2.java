package com.guods.stream;

import java.util.ArrayList;
import java.util.List;

public class StreamDemo2 {

	public static void main(String[] args) {
		List<User> userList = new ArrayList<User>();
		userList.add(new User(1, "材先生1"));
		userList.add(new User(2, "材先生2"));
		userList.add(new User(3, "材先生3"));
		userList.add(new User(4, "材先生4"));
		userList.add(new User(5, "材先生5"));
		
		userList.stream()
		.filter(user -> user.getAge() > 2)
		.forEach(user -> System.out.println(user.getName()));
	}
}
