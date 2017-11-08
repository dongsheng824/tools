package com.guods.proxy;

public interface Notify {

	void after(Object target);
	void before(Object target);
	void except(Object target);
	void finallyNotify(Object target);

}
