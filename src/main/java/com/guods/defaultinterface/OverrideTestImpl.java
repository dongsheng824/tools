package com.guods.defaultinterface;

public class OverrideTestImpl implements Defaultable {

	@Override
	public String defaultTest(String str) {
		return "world " + str;
	}

	
}
