package com.guods.modle;

public class User {

	private String name;
	private String mobile;
	
	public User() {
		super();
	}

	public User(String name, String mobile) {
		super();
		this.name = name;
		this.mobile = mobile;
	}

	public String print() {
		System.out.println("name:" + name);
		System.out.println("mobile:" + mobile);
		return name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
