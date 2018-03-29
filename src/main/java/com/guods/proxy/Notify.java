package com.guods.proxy;

public abstract class Notify {

	protected int order;

	abstract Object after(Object target);

	abstract Object before(Object target);

	abstract Object exception(Object target);

	abstract Object finallyNotify(Object target);

	protected Notify(int order) {
		super();
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
