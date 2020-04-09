package com.funcinter.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.funcinter.model.Employee;

public class Collections8 {

	static List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00));

	public static void main(String[] args) {
		Collections8 obj = new Collections8();
		obj.removeIf();
		obj.replaceAll();
		obj.sortNatural();
		obj.objectComparator();
		obj.objectComparatorMultiple();
		obj.objectComparatorWithNulls();
		obj.mapMerge();
		obj.createBiMap();
		obj.copyFromListOneToList2();
		obj.copyFromListOneToList2Name();
		obj.copyFromListOneToList2Name_filterBySalary();
		obj.mapMergeWithConcat();
		obj.mapMergeWithConcatWithDuplicates();
	}

	private void removeIf() {
		System.out.println("removeIf");
		// if Arrays.AsList is used to create a list, you cannot remove elements from
		// such a list
		Collection<String> col = Arrays.asList("one", "two", "three", "four");

		// If elements need to be removed, create a new ArrayList from the original one.
		Collection<String> col2 = new ArrayList<>(col);
		boolean b = col2.removeIf(s -> s.length() > 4);

		System.out.println(col2.stream().collect(Collectors.joining(",")));
	}

	private void replaceAll() {
		System.out.println("replaceAll");
		List<String> col = Arrays.asList("one", "two", "three", "four");
		List<String> col2 = new ArrayList<>(col);

		// col2.replaceAll(String::toUpperCase);
		col2.replaceAll(s -> s.toUpperCase());
		System.out.println(col2.stream().collect(Collectors.joining(",")));
	}

	private void sortNatural() {
		System.out.println("sortNatural");
		List<String> col = Arrays.asList("one", "two", "three", "four");
		List<String> col2 = new ArrayList<>(col);

		col2.sort(Comparator.naturalOrder());
		System.out.println(col2.stream().collect(Collectors.joining(",")));
	}

	private void objectComparator() {
		System.out.println("objectComparator");
		Comparator<Employee> compareSalary = Comparator.comparing(Employee::getSalary);
		employeeList.sort(compareSalary);
		employeeList.forEach(System.out::println);
	}

	private void objectComparatorMultiple() {
		System.out.println("objectComparatorMultiple");
		Comparator<Employee> compareSalary = Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName);
		employeeList.sort(compareSalary);
		employeeList.forEach(System.out::println);
	}

	private void objectComparatorWithNulls() {
		System.out.println("objectComparatorWithNulls");
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7500.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00), null);

		Comparator<Employee> compareSalary = Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName);

		employeeList.sort(Comparator.nullsFirst(compareSalary));
		employeeList.forEach(System.out::println);

		employeeList.sort(Comparator.nullsLast(compareSalary));
		employeeList.forEach(System.out::println);

	}

	private void mapMergeWithConcat() {
		System.out.println("mapMergeWithConcat");
		
		/*
		 * this will only work if there are no duplicate keys in the maps
		 */
		List<Employee> employeeList1 = Arrays.asList(new Employee("Tom Jones", 145, 7500.00),
				new Employee("Harry Major", 325, 110000.00), new Employee("Ethan Hardy", 245, 8000.00),
				new Employee("Nancy Smith", 422, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00));

		List<Employee> employeeList2 = Arrays.asList(new Employee("Tom Jones", 545, 7500.00),
				new Employee("Harry Major", 625, 110000.00), new Employee("Ethan Hardy", 945, 8000.00),
				new Employee("Nancy Smith", 722, 12000.00), new Employee("Deborah Sprightly", 822, 8000.00));

		Map<Integer, List<Employee>> mapByAge1 = employeeList1.stream()
				.collect(Collectors.groupingBy(Employee::getAge));
		Map<Integer, List<Employee>> mapByAge2 = employeeList2.stream()
				.collect(Collectors.groupingBy(Employee::getAge));

		Map<Object, Object> allObjects = Stream.concat(mapByAge1.entrySet().stream(), mapByAge2.entrySet().stream())
				.collect(Collectors.toMap(entry -> entry.getKey(), // The key
						entry -> entry.getValue() // The value
				));
		allObjects.forEach((key, value) -> System.out.println(key + "->" + value));
		

	}

	private void mapMergeWithConcatWithDuplicates() {
		System.out.println("mapMergeWithConcatWithDuplicates");
		
		List<Employee> employeeList1 = Arrays.asList(new Employee("Tom Jones", 45, 7500.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00));

		List<Employee> employeeList2 = Arrays.asList(new Employee("Tom Jones", 45, 7500.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00));

		Map<Integer, List<Employee>> mapByAge1 = employeeList1.stream()
				.collect(Collectors.groupingBy(Employee::getAge));
		Map<Integer, List<Employee>> mapByAge2 = employeeList2.stream()
				.collect(Collectors.groupingBy(Employee::getAge));

		Map<Integer, List<Employee>> allObjects = Stream.concat(mapByAge1.entrySet().stream(), mapByAge2.entrySet().stream())
				.collect(Collectors.toMap(entry -> entry.getKey(), // The key
						entry -> entry.getValue(), // The value
						// here we need a merge rule which will be invoked only for duplicate keys
						// out of two entries with key Age=22 we need to create one age 22 key with value = merged lists
						(list1, list2) -> {
							list1.addAll(list2);
							return list1;
							
						}
				));
		allObjects.forEach((key, value) -> System.out.println(key + "->" + value));
		

	}
	
	private void mapMerge() {
		System.out.println("mapMerge");
		List<Employee> employeeList1 = Arrays.asList(new Employee("Tom Jones", 45, 7500.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00));

		List<Employee> employeeList2 = Arrays.asList(new Employee("Tom Jones", 45, 7500.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 8000.00));

		Map<Integer, List<Employee>> mapByAge1 = employeeList1.stream()
				.collect(Collectors.groupingBy(Employee::getAge));
		Map<Integer, List<Employee>> mapByAge2 = employeeList2.stream()
				.collect(Collectors.groupingBy(Employee::getAge));

		// merge map1 into map2
		mapByAge2.entrySet().stream()
				.forEach(entry -> mapByAge1.merge(entry.getKey(), entry.getValue(), (list1, list2) -> {
					list1.addAll(list2);
					return list1;
				}));

		mapByAge2.forEach((key, value) -> System.out.println(key + "->" + value));
	}

	private void createBiMap() {
		System.out.println("createBiMap");

		// outer map is by age, inner map is by salary
		Map<Integer, Map<Double, List<Employee>>> bimap = new HashMap<>();

		employeeList.forEach(employee -> bimap.computeIfAbsent(employee.getAge(), HashMap::new) // if age is present,
																								// return map, else
																								// create a new one
				.merge(employee.getSalary(), // same for the inner map
						new ArrayList<>(Arrays.asList(employee)), (list1, list2) -> {
							list1.addAll(list2);
							return list1;
						}));

		bimap.forEach((key, value) -> System.out.println(key + "->" + value));

	}

	private void copyFromListOneToList2() {
		System.out.println("copyFromListOneToList2");

		List<Employee> newlist = new ArrayList<>();

		IntStream.range(0, employeeList.size()).mapToObj(i -> employeeList.get(i)).forEach(newlist::add);

		newlist.forEach((v) -> System.out.println(v));

	}

	private void copyFromListOneToList2Name() {
		System.out.println("copyFromListOneToList2Name");

		List<String> newlist = new ArrayList<>();

		IntStream.range(0, employeeList.size()).mapToObj(i -> employeeList.get(i).getName()).forEach(newlist::add);

		newlist.forEach((v) -> System.out.println(v));

	}

	private void copyFromListOneToList2Name_filterBySalary() {
		System.out.println("copyFromListOneToList2Name_filterBySalary");

		List<String> newlist = new ArrayList<>();

		IntStream.range(0, employeeList.size()).filter(i -> employeeList.get(i).getSalary() > 8001.00)
				.mapToObj(i -> employeeList.get(i).getName()).forEach(newlist::add);

		newlist.forEach((v) -> System.out.println(v));

	}

}
