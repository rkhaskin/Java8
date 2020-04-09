package com.funcinter.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {
	List<String> list = Arrays.asList("one", "two", "three");
	
	List<String> newList = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConsumerExample obj = new ConsumerExample();
		obj.processConsumerLambda();
		obj.processConsumerRefMeth();
		obj.processOldWay();
		
	}

	private  void processConsumerLambda() {
		Consumer<String> s1 = s -> System.out.println(s);
		Consumer<String> s2 = s -> newList.add(s);
		
		list.forEach(s1.andThen(s2));
	}

	private  void processConsumerRefMeth() {
		Consumer<String> s1 = System.out::println;
		Consumer<String> s2 = newList::add;
		
		list.forEach(s1.andThen(s2));
	}

	
	private void processOldWay() {
		for (String str : list) {
			System.out.println(str);
			newList.add(str);
		}
	}

}
