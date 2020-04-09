package com.funcinter.lookup;

import java.util.Arrays;
import java.util.List;

import com.funcinter.model.Employee;

public class Lookup {

	static List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00));

	public static void main(String[] args) {
		Lookup obj = new Lookup();
		obj.findPeekTerminal();
		obj.findPeekCount();
		obj.findAgeSpecific();
		obj.findMultiAgeSpecific();
		obj.findMultiAgeSpecificIfAllExist();
		obj.findFirst();
	}

	private void findPeekTerminal() {
		employeeList
		.stream()
		.peek(System.out::println);
	}

	private void findPeekCount() {
		employeeList
		 .stream()
		 .peek(System.out::println)
		 .count();
	}
	
	private void findAgeSpecific() {
		employeeList
			.stream()
			.filter(elem -> elem.getAge() == 45)
			.peek(elem -> {
				System.out.println("filtered elements: "+ elem);
			})
			.count();
	}

	private void findMultiAgeSpecific() {
		long count = employeeList
			.stream()
			.filter(elem -> elem.getAge() == 45 || elem.getAge() == 25)
			.count();
		System.out.println("findMultiAgeSpecific count = " + count);
	}

	private void findMultiAgeSpecificIfAllExist() {
		boolean allPresent = employeeList
			.stream()
			.map(Employee::getAge) // without map distinct will apply to the Employee object and not to age
			.filter(elem -> elem == 45 || elem == 27)
			.distinct() // eliminate duplicates
			.limit(2) // could be max 2 or 1 or 0
			.count() == 2;
		
		System.out.println("findMultiAgeSpecificIfAllExist allPresent = " + allPresent);
	}
	
	private void findFirst() {
		System.out.println("(findFirst element if Present)");
		employeeList
		  .stream()
		  .findFirst()
		  .ifPresent(System.out::println);
	}
	
}
