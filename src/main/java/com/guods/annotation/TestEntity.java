package com.guods.annotation;

import java.util.Date;

public class TestEntity {

	@StringSetter("测试")
	private String name;
	
	@StringSetter("杭州市")
	private String address;
	
	@DateFormate("yyyy-MM-dd")
	private Date date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "TestEntity [name=" + name + ", address=" + address + ", date=" + date + "]";
	}
	
}
