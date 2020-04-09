package com.funcinter.reduce;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.funcinter.model.Employee;

/*
 * In mathematics, an identity element, or neutral element, is a special type of element of a set with respect to a 
 * binary operation on that set, which leaves any element of the set unchanged when combined with it.[1] [2][3] 
 * This concept is used in algebraic structures such as groups and rings. The term identity element is often shortened 
 * to identity (as in the case of additive identity and multiplicative identity),[4] when there is no possibility of 
 * confusion, but the identity implicitly depends on the binary operation it is associated with.
 */

public class Reduce {

	static List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00));

	public static void main(String[] args) {
		Reduce obj = new Reduce();
		obj.sumDouble_Lambda();
		obj.sumDouble_MethodRef();
		obj.maxSalaryFromObject();
		obj.maxSalary_working();
		obj.maxSalary_not_working();
		obj.maxSalary_fixed();
		obj.maxSalary_ifPresent();
		obj.maxSalary_ifPresentLambda();
		obj.maxSalary_MethRef();
		obj.maxSalary_Comparator();
		obj.maxSalaryEmployeeObject_Comparator();
		obj.namesAsCommaSeparatedString();
		obj.joinNamesAndPhrases();
		obj.namesAsList();
		obj.ageAsMap();
		obj.ageAndCountAsMap();
		obj.MapAsStringAndObject();
		obj.MapAsStringAndObjectLambdaFunction();
		obj.MapAsStringAndObjectLambdaLambda();
		obj.MapWithDuplicateWithError();
		obj.MapWithDuplicateResolved();
		obj.MapWithDuplicateResolvedWithFuncInterfaces();
		obj.MapWithDuplicateResolvedWithFuncInterfacesOld();
		obj.MapWithDuplicateResolvedLinkedHashMap();
		obj.MapWithDuplicateResolvedLinkedHashMapSupplier();
		obj.getAverageSalary();
		obj.getSummary();
		obj.reduceToNewObject();
	}

	private void sumDouble_Lambda() {
		Double totalSalaryExpense = employeeList //
				.stream() //
				.map(emp -> emp.getSalary()) //
				.reduce(0.00, (a, b) -> a + b);
		System.out.println("(Lambda) Total salary expense: " + totalSalaryExpense);
	}

	private void sumDouble_MethodRef() {
		Double totalSalaryExpense = employeeList //
				.stream() //
				.map(emp -> emp.getSalary()) //
				.reduce(0.00, Double::sum);
		System.out.println("(Method ref) Total salary expense: " + totalSalaryExpense);
	}

	/*
	 * This example uses the overloaded variant of
	 * reduce() method which doesn’t take the initial
	 * value and returns an Optional<Employee> type of
	 * value containing the result.
	 */
	private void maxSalaryFromObject() {
		Optional<Employee> maxSalaryEmp = employeeList //
				.stream() //
				.reduce((Employee a, Employee b) -> a.getSalary() < b.getSalary() ? b : a);
		if (maxSalaryEmp.isPresent())
			System.out.println("(Object) Employee with max salary: " + maxSalaryEmp.get());
		else
			System.out.println("(Object) Employee with max salary: List is empty");

	}

	private void maxSalary_working() {
		Double res = employeeList.stream().map(Employee::getSalary).reduce(0.00, (a, b) -> a < b ? b : a);

		System.out.println("(max salary working when the data is right) max is " + res);
	}

	private void maxSalary_not_working() {
		List<Employee> employeeList = Arrays.asList(new Employee("Deborah Sprightly", 29, -9000.00));

		Double res = employeeList.stream().map(Employee::getSalary).reduce(0.00, (a, b) -> a < b ? b : a);

		System.out.println("(max salary not working) max is " + res);
	}

	private void maxSalary_fixed() {
		List<Employee> employeeList = Arrays.asList(new Employee("Deborah Sprightly", 29, -9000.00));

		Optional<Double> res = employeeList
				.stream()
				.map(Employee::getSalary)
				.reduce((a, b) -> a < b ? b : a);

		if (res.isPresent())
			System.out.println("(max salary fixed) max is " + res.get());
	}

	private void maxSalary_ifPresent() {
		System.out.println("maxSalary_ifPresent");
		List<Employee> employeeList = Arrays.asList(new Employee("Deborah Sprightly", 29, -9000.00));

		employeeList
				.stream()
				.map(Employee::getSalary)
				.reduce((a, b) -> a < b ? b : a)
				.ifPresent(System.out::println);
	}

	private void maxSalary_ifPresentLambda() {
		System.out.println("maxSalary_ifPresentLambda");
		List<Employee> employeeList = Arrays.asList(new Employee("Deborah Sprightly", 29, -9000.00));

		employeeList
				.stream()
				.map(Employee::getSalary)
				.reduce((a, b) -> a < b ? b : a)
				.ifPresent(res -> System.out.println(res));
	}
	
	private void maxSalary_Comparator() {
		Optional<Double> res = employeeList
				.stream()
				.map(Employee::getSalary)
				.max(Comparator.comparing(elem -> elem));

		if (res.isPresent())
			System.out.println("(maxSalary_Comparator) max is " + res.get());
	}
	
	private void maxSalaryEmployeeObject_Comparator() {
		Optional<Employee> res = employeeList
				.stream()
				.max(Comparator.comparing(Employee::getSalary));

		if (res.isPresent())
			System.out.println("(maxSalaryEmployeeObject_Comparator) max is " + res.get());
	}
	private void maxSalary_MethRef() {
		List<Employee> employeeList = Arrays.asList(new Employee("Deborah Sprightly", 29, -9000.00));

		Optional<Double> res = employeeList.stream().map(Employee::getSalary).reduce(Double::max);

		if (res.isPresent())
			System.out.println("(max salary meth ref) max is " + res.get());
	}

	private void namesAsCommaSeparatedString() {
		String names = employeeList.stream() //
				.map(Employee::getName) //
				.collect(Collectors.joining(", "));
		
		System.out.println("(namesAsCommaSeparatedString)" + names);
	}

	
	private void joinNamesAndPhrases() {
		System.out.println("joinNamesAndPhrases");
		String phrase = employeeList
			    .stream()
			    .filter(p -> p.getAge() >= 18)
			    .map(p -> p.getName())
			    .collect(Collectors.joining(" and ", "At work ", " drink coffee.")); // delim, prfx, sfx

			System.out.println(phrase);
	}
	
	private void namesAsList() {
		List<String> names = employeeList.stream() //
				.map(Employee::getName) //
				.collect(Collectors.toList());
		
		System.out.println("(nameAsList results : )");
		names.forEach(System.out::println);
	}
	
	private void ageAsMap() {
		Map<Integer, List<Employee>> names = employeeList //
				.stream() //
				.collect(Collectors.groupingBy(Employee::getAge));
		
		System.out.println("(ageAsMap results : )");
		
		names.forEach((key, obj) -> System.out.format("age %s: %s\n", key, obj));
		
		/* same as above */
//		names.keySet().forEach(elem -> {
//			System.out.println("key = " + elem);
//			names.get(elem).forEach(System.out::println);
//		});
	}

	private void ageAndCountAsMap() {
		Map<Integer, Long> names = employeeList //
				.stream() //
				.collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));
		
		System.out.println("(ageAsMap results : )");
		names.forEach((key, obj) -> System.out.println("key = " + key + " value = " + names.get(key)));
	}
	
	private void MapAsStringAndObject() {
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(Employee::getName, Function.identity()));

		System.out.println("(MapAsStringAndObject results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
	}
	
	private void MapAsStringAndObjectLambdaFunction() {
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(elem -> elem.getName(), Function.identity()));

		System.out.println("(MapAsStringAndObjectLambda results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
	}	
	
	private void MapAsStringAndObjectLambdaLambda() {
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(elem -> elem.getName(), elem -> elem));

		System.out.println("(MapAsStringAndObjectLambdaLambda results : )");
		map.forEach((key, obj) -> {
			System.out.println("key = " + key + " value = " + obj);
		});
	}
	
	private void MapWithDuplicateWithError() {
		System.out.println("(MapWithDuplicateWithError)");
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00),
				new Employee("Harry Major", 28, 150000.00));
		
		// will throw a duplicate key exception
		try {
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(elem -> elem.getName(), elem -> elem));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void MapWithDuplicateResolved() {
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00),
				new Employee("Harry Major", 28, 150000.00));

		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(elem -> elem.getName(), elem -> elem, (oldValue, newValue) -> newValue));

		System.out.println("(MapWithDuplicateResolved results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
	}
	
	private void MapWithDuplicateResolvedWithFuncInterfaces() {
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00),
				new Employee("Harry Major", 28, 150000.00));

		BinaryOperator<Employee> bo = (oldValue, newValue) -> newValue;
		Function<Employee, String> fname = elem -> elem.getName();
		Function<Employee, Employee> femployee = elem -> elem;
		
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(fname, femployee, bo));

		System.out.println("(MapWithDuplicateResolvedWithFuncInterfaces results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
	}

	private void MapWithDuplicateResolvedWithFuncInterfacesOld() {
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00),
				new Employee("Harry Major", 28, 150000.00));

		BinaryOperator<Employee> bo = new BinaryOperator<Employee>() {
			@Override
			public Employee apply(Employee olderEmp, Employee newerEmp) {
				return olderEmp.getName().equals(newerEmp.getName()) ? newerEmp : olderEmp;
			}
		};
				
		Function<Employee, String> fname = new Function<Employee, String>() {
			@Override
			public String apply(Employee t) {
				return t.getName();
			}
		};
				
		Function<Employee, Employee> femployee = new Function<Employee, Employee>() {
			@Override
			public Employee apply(Employee t) {
				return t;
			}
		};
				
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(fname, femployee, bo));

		System.out.println("(MapWithDuplicateResolvedWithFuncInterfacesOld results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
	}
	
	private void MapWithDuplicateResolvedLinkedHashMap() {
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00),
				new Employee("Harry Major", 28, 150000.00));

		// get a particular map type
		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(
						elem -> elem.getName(), 
						elem -> elem, (oldValue, newValue) -> newValue,
				        LinkedHashMap::new));

		System.out.println("(MapWithDuplicateResolvedLinkedHashMap results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
		System.out.println("map type = " + map.getClass().getTypeName());
	}	

	private void MapWithDuplicateResolvedLinkedHashMapSupplier() {
		List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
				new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
				new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00),
				new Employee("Harry Major", 28, 150000.00));

//		Supplier<LinkedHashMap<String, Employee>> supplier = () -> new LinkedHashMap<String, Employee>();
		Supplier<LinkedHashMap<String, Employee>> supplier = LinkedHashMap::new;

		Map<String, Employee> map = employeeList
				.stream()
				.collect(Collectors.toMap(
						elem -> elem.getName(), 
						elem -> elem, (oldValue, newValue) -> newValue,
				        supplier));

		System.out.println("(MapWithDuplicateResolvedLinkedHashMapSupplier results : )");
		map.keySet().forEach(key -> {
			System.out.println("key = " + key + " value = " + map.get(key));
		});
		System.out.println("map type = " + map.getClass().getTypeName());
	}	
	
	private void getAverageSalary() {
		System.out.println("getAverageSalary");
		Double avgSalary = employeeList
				.stream()
				.collect(Collectors.averagingDouble(Employee::getSalary));
		
		System.out.println("average salery = " + avgSalary);
	}
	
	private void getSummary() {
		System.out.println("getSummary");
		IntSummaryStatistics ageSummary =
				employeeList
			        .stream()
			        .collect(Collectors.summarizingInt(p -> p.getAge()));

			System.out.println(ageSummary);	
	}
	
	/*
	 * calls with 
	 */
	private void reduceToNewObject() {
		Employee result =
			    employeeList
			        .stream()
			        .reduce(new Employee("John Doe", 38, 80000.00), (p1, p2) -> { // create a new object and concat all names and 
			        	                                                              // add up all ages.
			            p1.setAge(p1.getAge() + p2.getAge());
			            p1.setName(p1.getName()+p2.getName());
			            return p1;
			        });

			System.out.format("name=%s; age=%s", result.getName(), result.getAge());
	}

}
