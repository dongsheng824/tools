package com.guods.tools.model;

public class User {

	private Long id;
	private String username;
	
	public User() {
		super();
	}
	public User(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
