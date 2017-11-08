package com.guods.proxy;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
	public static void main(String[] args) {
		TestModel testModel = new TestModelImpl("aaaaaaaaaa", 10);
		Notify notify = new Notify() {
			
			public void before(Object target) {
				TestModelImpl mytarget = ((TestModelImpl)target);
				System.out.println("+++++++this is before++++++++++" + mytarget.getAttr2());
				mytarget.setAttr2(mytarget.getAttr2() + 1);
			}
			
			public void after(Object target) {
				TestModelImpl mytarget = ((TestModelImpl)target);
				mytarget.setAttr2(mytarget.getAttr2() + 1);
				System.out.println("++++++++this is after+++++++++++++" + mytarget.getAttr2());
			}

			public void except(Object target) {
				TestModelImpl mytarget = ((TestModelImpl)target);
				mytarget.setAttr2(mytarget.getAttr2() + 1);
				System.out.println("++++++++this is exception+++++++++++++" + mytarget.getAttr2());
			}

			public void finallyNotify(Object target) {
				TestModelImpl mytarget = ((TestModelImpl)target);
				mytarget.setAttr2(mytarget.getAttr2() + 1);
				System.out.println("++++++++this is finally+++++++++++++" + mytarget.getAttr2());
			}
		};
		TestModel proxyInstance = new MyInvocationHandler(testModel, notify).getProxyInstance();
		proxyInstance.printTest();
		System.out.println(".....................");
		proxyInstance.printNormal();
	}
}
