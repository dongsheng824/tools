package com.guods.proxy;

public class TestModelImpl implements TestModel {

	private String attr1;
	private int attr2;

	// cglib代理必须要有一个无参的构造方法
	public TestModelImpl() {
		super();
	}

	public TestModelImpl(String attr1, int attr2) {
		super();
		this.attr1 = attr1;
		this.attr2 = attr2;
	}

	public void printTest() {
		System.out.println("the attribute attr2 is:" + attr2);
		printInner();
	}

	public void printInner() {
		System.out.println("this is inner method");
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public void normalMethod() {
		System.out.println("======this is nomral function=======");
	}

	public int getAttr2() {
		return attr2;
	}

	public void setAttr2(int attr2) {
		this.attr2 = attr2;
	}

}
