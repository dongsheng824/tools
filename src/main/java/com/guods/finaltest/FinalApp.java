package com.guods.finaltest;

import com.guods.modle.Const;
import com.guods.modle.User;

public class FinalApp {

	public static void main(String[] args) {
		User user = new User();
		user.setName("材先生");
		Const.ADMIN.add(user);
		User user2 = new User();
		user2.setName("王先生");
		Const.ADMIN.add(user2);
		System.out.println(Const.ADMIN.get(0).getName());
	}
}
