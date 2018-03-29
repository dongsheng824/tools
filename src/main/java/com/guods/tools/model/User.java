package com.guods.tools.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private List<String> addr;
	
	public User() {
		super();
	}
	
	public User(Long id, String username, List<String> addr) {
		super();
		this.id = id;
		this.username = username;
		this.addr = addr;
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
	
	public List<String> getAddr() {
		return addr;
	}
	public void setAddr(List<String> addr) {
		this.addr = addr;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
	
}
