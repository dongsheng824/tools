package com.guods.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaDemo {

	public static void main(String[] args) {
        //Function
        System.out.println("Function:入参+返回");
        Function<String, String> func = (t)->{return t  + ":" + t;};
        String r = func.apply("hello");
        System.out.println(r);
        System.out.println("==========================================");
        //Consumer
        System.out.println("Consumer:入参+void");
        Consumer<Integer> square = (i)->{System.out.println(i + " square = " + i*i);};
        square.accept(10);
        System.out.println("==========================================");
        //Supplier
        System.out.println("Supplier:没有入参+返回");
        Supplier<Float> pi = ()->{return (float) 3.14159;};
        System.out.println(pi.get());
        System.out.println("==========================================");
        //Predicate
        System.out.println("Predicate:入参+返回boolean");
        Predicate<Integer> check = (i)->{return i == 1 ? true : false;};
        System.out.println(check.test(1));
        System.out.println("==========================================");
        System.out.println(check);
        //函数作为参数用lambda
        System.out.println("函数作为参数用lambda:");
        String dd = doubleStr("hello world", (t)->{return t  + ":" + t;});
        System.out.println(dd);
        System.out.println("==========================================");
        //函数作为参数用lambda
        System.out.println("函数作为参数不用lambda:");
        String ee = doubleStr("hello world", new Function<String, String>() {

			@Override
			public String apply(String t) {
				return t  + ":" + t;
			}
		});
        System.out.println(ee);
        System.out.println("==========================================");
        //lambda表达式替代匿名类
        System.out.println("lambda创建线程：");
        new Thread(()->System.out.println("hello thread")).start();
        System.out.println("==========================================");
        //lambda 迭代
        System.out.println("lambda迭代集合");
        List<Integer> testList = Arrays.asList(1, 2, 3, 4, 5);
        testList.forEach((i)->{i = i * i; System.out.println(i);});
        testList.forEach((i)->System.out.println(i));
        System.out.println("==========================================");
        //stream
        System.out.println("stream:列表过滤");
        testList.stream().filter((i)->i>3).forEach(i->System.out.println(i));
        System.out.println("==========================================");
        //lambda map reduce
        System.out.println("map reduce, 每个值平方再相加：");
        Integer reduce = testList.stream().map((i)->{return i*i;}).reduce(0, (sum, item)-> sum + item + 1);
        System.out.println(reduce);
	}
	
	/**
	 * 函数作为参数的案例
	 * 
	 * 看上去就是一个接口，只不过接口的实现可以用lambda来表示：(t)->{return t  + ":" + t;}
	 * 
	 * @param r
	 * @param func
	 * @return
	 */
	public static String doubleStr(String r, Function<String, String> func) {
		String lambdaResult = func.apply(r);
		return "This lambda result:" + lambdaResult;
	}
	
}
