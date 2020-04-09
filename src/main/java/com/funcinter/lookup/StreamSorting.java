package com.funcinter.lookup;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.funcinter.model.Employee;

public class StreamSorting {

	static Employee[] employeeArray = new Employee[] {
			new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00),
			new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00),
			new Employee("Deborah Sprightly", 22, 9000.00)
	};
	
	public static void main(String[] args) {
		StreamSorting obj = new StreamSorting();
		obj.badSort();
		obj.goodSort();
		obj.sortReversedOrder();
		obj.sortEmployeeList();
		obj.sortEmployeeListDesc();
	}

	/*
	 * Sorting is a special kind of intermediate operation. It's a so called stateful operation since 
	 * in order to sort a collection of elements you have to maintain state during ordering.
	 */
    private void badSort() {
    	System.out.println("(badSort");
    	Stream.of("d2", "a2", "b1", "b3", "c")
        .sorted((s1, s2) -> {
            System.out.printf("sort: %s; %s\n", s1, s2);
            return s1.compareTo(s2);
        })
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
    
    /*
     * In this example sorted is never been called because filter reduces the input collection to just one element. 
     * So the performance is greatly increased for larger input collections
     */
    private void goodSort() {
    	System.out.println("(goodSort");
    	Stream.of("d2", "a2", "b1", "b3", "c")
        .filter(s -> {
            System.out.println("filter: " + s);
            return s.startsWith("a");
        })
        .map(s -> {
            System.out.println("map: " + s);
            return s.toUpperCase();
        })
        .sorted((s1, s2) -> {
            System.out.printf("sort: %s; %s\n", s1, s2);
            return s1.compareTo(s2);
        })
        .forEach(s -> System.out.println("forEach: " + s));
	}
    
    private void sortReversedOrder() {
    	System.out.println("(sortReversedOrder)");
    	List<String> list = Arrays.asList("9", "A", "Z", "1", "B", "Y", "4", "a", "c");
    	List<String> sortedList = list.stream()
    			.sorted(Comparator.reverseOrder())
    			.collect(Collectors.toList());

            sortedList.forEach(System.out::println);    	
	}
    
    private void sortEmployeeList() {
    	System.out.println("(sortEmployeeList");
    	List<Employee> list = Arrays.asList(employeeArray);
    	
    	List<Employee> sortedList = list
    	  .stream()
    	  .sorted(Comparator.comparingDouble(Employee::getSalary))
    	  .collect(Collectors.toList());
    	
    	sortedList.stream().forEach(System.out::println);
    }

    private void sortEmployeeListDesc() {
    	System.out.println("(sortEmployeeList");
    	List<Employee> list = Arrays.asList(employeeArray);
    	
    	List<Employee> sortedList = list
    	  .stream()
    	  .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
    	  .collect(Collectors.toList());
    	
    	sortedList.stream().forEach(System.out::println);
    }
    

}
