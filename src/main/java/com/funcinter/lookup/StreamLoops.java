package com.funcinter.lookup;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.funcinter.model.Employee;

public class StreamLoops {

	static Employee[] employeeArray = new Employee[] {
			new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00),
			new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00),
			new Employee("Deborah Sprightly", 22, 9000.00)
	};
	
	public static void main(String[] args) {
		StreamLoops obj = new StreamLoops();
		obj.ForLoop();
		obj.primitiveStreams();
	}

	private void ForLoop() {
		System.out.println("(For loop)");
		IntStream
		.range(1, 4) // start - inclusive, end - exclusive. Will return 1, 2, 3
	    .forEach(System.out::println);		
	}
	
	/*
	 * All those primitive streams work just like regular object streams with the following differences: 
	 * Primitive streams use specialized lambda expressions, e.g. IntFunction instead of Function 
	 * or IntPredicate instead of Predicate.  And primitive streams support the additional terminal aggregate 
	 * operations sum() and average():
	 */
	private void primitiveStreams() {
		System.out.println("(primitiveStreams)");
		Arrays
		.stream(new int[] {1, 2, 3})
	    .map(n -> 2 * n + 1)
	    .average()
	    .ifPresent(System.out::println);  // 5.0
	}
	
}
