package com.funcinter.lookup;

import java.util.stream.Stream;

import com.funcinter.model.Employee;

public class StreamsFromArrays {

	static Employee[] employeeArray = new Employee[] {
			new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00),
			new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00),
			new Employee("Deborah Sprightly", 22, 9000.00)
	};
	
	public static void main(String[] args) {
		StreamsFromArrays obj = new StreamsFromArrays();
		obj.findFirstInArray();
	}

	
	private void findFirstInArray() {
		System.out.println("(findFirstInArray)");
		Stream
		  .of(employeeArray)
		  .findFirst()
		  .ifPresent(System.out::println);
	}
	
}
