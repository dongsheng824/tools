package com.guods.proxy;

public class TestModelImpl implements TestModel {

	private String attr1;
	private int attr2;
	
	
	public TestModelImpl() {
		super();
	}

	public TestModelImpl(String attr1, int attr2) {
		super();
		this.attr1 = attr1;
		this.attr2 = attr2;
	}

	public void printTest(){
		int a = 1/0;
		System.out.println("the attribute attr2 is:" + attr2);
	}


	public String getAttr1() {
		return attr1;
	}


	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}


	public void printNormal() {
		System.out.println("======this is nomral function=======");		
	}


	public int getAttr2() {
		return attr2;
	}


	public void setAttr2(int attr2) {
		this.attr2 = attr2;
	}
	
}
