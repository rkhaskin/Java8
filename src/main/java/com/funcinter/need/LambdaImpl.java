package com.funcinter.need;

import java.util.function.Function;

@FunctionalInterface
interface Age3 {
	Integer getAge(int num);
}

@FunctionalInterface
interface Age4 {
	static int num = 43;
	void getAge();
}

public class LambdaImpl {

	public static void main(String[] args) {
		//implement a method on an interface.
		LambdaImpl obj = new LambdaImpl();
		obj.customInterface();
		obj.functionInterface();
		obj.customInterface_empty();
		obj.functionInterface_empty();
	}

	/*
	 * create a custom interface, instantiate it thru
	 * a lambda and invoke.
	 */
	private void customInterface() {
		Age3 age3 = (num) -> num * 2;
		// it will be called because it is the only available abstract method on the interface. 
		int result = age3.getAge(21);
		System.out.println("custom interface res = " + result);
	}

	/*
	 * use a provided functional interface instead of
	 * creating a custom one
	 */
	private void functionInterface() {
		Function<Integer, Integer> age = num -> num * 2;
		System.out.println("Function interface = " + age.apply(21));
	}

	/*
	 * create a custom interface, instantiate it thru
	 * a lambda and invoke.
	 * No arg, no return interface
	 */
	private void customInterface_empty() {
		Age4 age4 = () -> System.out.println("(customInterface_empty). res = " + Age4.num);
		age4.getAge();
	}

	/*
	 * use a provided functional interface instead of
	 * creating a custom one
	 * No arg, no return interface
	 */
	private void functionInterface_empty() {
		Runnable age = () -> {
			System.out.println("(Runnable) Function interface = " + 21);
		};
		
		age.run();
	}	
}
