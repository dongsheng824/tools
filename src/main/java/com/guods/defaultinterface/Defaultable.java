package com.guods.defaultinterface;

public interface Defaultable {

	default String defaultTest(String str) {
		return "hello " + str;
	}
}
