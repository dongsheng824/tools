package com.guods.asm;

/**
 * 编译：javac TestModel.java
 * 生成jvm指令：javap -c -v TestModel.class
 * 
 * javac -g TestModel.java，编译生成调试信息：有方法参数名
 * javac -g:none TestModel.java，编译不生成调试信息:么有方法参数名
 * 
 * @author guods
 *
 */
public class TestModel {

	public int add(int a, int b) {
		return a + b;
	}
	
	public void add2() {
		int i = 10;
		int j = 10000;
		int k = i + j;
		System.out.println(k);
	}
	
	public void add3() {
		int i;
		for (i = 0; i < 5; i++) {
			i++;
		}
		System.out.println(i);
	}
}

/**jvm指令
Compiled from "TestModel.java"
public class com.guods.asm.TestModel {
  public com.guods.asm.TestModel();
    descriptor: ()V
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public int add(int, int);
    descriptor: (II)I
    Code:
       0: iload_1
       1: iload_2
       2: iadd
       3: ireturn

  public void add2();
    descriptor: ()V
    Code:
       0: bipush        10
       2: istore_1
       3: sipush        10000
       6: istore_2
       7: iload_1
       8: iload_2
       9: iadd
      10: istore_3
      11: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      14: iload_3
      15: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
      18: return

  public void add3();
    descriptor: ()V
    Code:
       0: iconst_0
       1: istore_1
       2: iload_1
       3: iconst_5
       4: if_icmpge     16
       7: iinc          1, 1
      10: iinc          1, 1
      13: goto          2
      16: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      19: iload_1
      20: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
      23: return
}
 */
