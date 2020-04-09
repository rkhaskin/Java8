package com.funcinter.lookup;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.funcinter.model.Employee;

public class StreamCasting {

	static Employee[] employeeArray = new Employee[] {
			new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00),
			new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00),
			new Employee("Deborah Sprightly", 22, 9000.00)
	};
	
	public static void main(String[] args) {
		StreamCasting obj = new StreamCasting();
		obj.strToInt();
		obj.primToObj();
		obj.primToObjToList();
		obj.verticalOrder();
		obj.verticalOrderBenefit();
		obj.badOrder();
		obj.goodOrder();
	}

    
	private void strToInt() {
		System.out.println(("strToInt"));
		Stream.of("a1", "a2", "a3")
	    .map(s -> s.substring(1))
	    .mapToInt(Integer::parseInt) // convert to int
	    .max()
	    .ifPresent(System.out::println);  // 3
	}
	
	private void primToObj() {
		System.out.println("(primToObj)");
		IntStream.range(1, 4)
	    .mapToObj(i -> new Employee("AA", 47, i*100.00)) 
	    .forEach(System.out::println);
	}

	private void primToObjToList() {
		System.out.println("(primToObjToList)");
		List<Employee> list = IntStream.range(1, 4)
	    .mapToObj(i -> new Employee("AA", 47, i*100.00)) 
	    .collect(Collectors.toList());
		
		list.forEach(System.out::println);
	}
	
	/*
	 * The order of the result might be surprising. A naive approach would be to execute the operations horizontally 
	 * one after another on all elements of the stream. But instead each element moves along the chain vertically. 
	 * The first string "d2" passes filter then forEach, only then the second string "a2" is processed
	 */
	private void verticalOrder() {
		System.out.println("(verticalOrder)");
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return true;
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
	}
	
	/*
	 * The operation anyMatch returns true as soon as the predicate applies to the given input element. 
	 * This is true for the second element passed "A2". Due to the vertical execution of the stream chain, 
	 * map has only to be executed twice in this case. 
	 * So instead of mapping all elements of the stream, map will be called as few as possible.
	 */
	private void verticalOrderBenefit() {
		System.out.println("(verticalOrderBenefit)");
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .anyMatch(s -> {
	        System.out.println("anyMatch: " + s);
	        return s.startsWith("A");
	    });
	}
	
	/*
	 * As you might have guessed both map and filter are called 
	 * five times for every string in the underlying collection whereas forEach is only called once.
     * We can greatly reduce the actual number of executions if we change the order of the operations, 
     * moving filter to the beginning of the chain:
	 */
	private void badOrder() {
		System.out.println("(badOrder)");
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("A");
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
	}
	
	private void goodOrder() {
		System.out.println("(goodOrder)");
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("a");
	    })
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
		
	}

}
