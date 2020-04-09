package com.funcinter.lookup;

import java.util.Arrays;
import java.util.List;

import com.funcinter.model.Employee;

/*
 * The reduction operation combines all elements of the stream into a single result. 
 * Java 8 supports three different kind of reduce methods. 
 * The first one reduces a stream of elements to exactly one element of the stream. 
 * Let's see how we can use this method to determine the oldest person:
 */

public class Reducer {

	static List<Employee> employeeList = Arrays.asList(new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00));
	
	public static void main(String[] args) {
		Reducer obj = new Reducer();
		obj.reduceToOne();
		obj.reduceToAccumulate();
		obj.reduceThirdWay();
		obj.reduceThirdWay_CombinerNotCalled();
		obj.reduceThirdWay_parallel();
	}
	
	private void reduceToOne() {
		/*
		 * The reduce method accepts a BinaryOperator accumulator function. That's actually a BiFunction where both operands 
		 * share the same type, in that case Person. BiFunctions are like Function but accept two arguments. 
		 * The example function compares both persons ages in order to return the person with the maximum age.
		 */
		
		employeeList
	    .stream()
	    .reduce((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2)
	    .ifPresent(System.out::println);    
	}
	
	/*
	 * The second reduce method accepts both an identity value and a BinaryOperator accumulator. 
	 * This method can be utilized to construct a new Employee with the aggregated salary and ages from all other employees in the stream
	 */
	private void reduceToAccumulate() {
		Employee result =
				employeeList
			        .stream()
			        .reduce(new Employee("", 0, 0.00), (p1, p2) -> {
			            p1.setAge(p1.getAge() + p2.getAge());
			            p1.setSalary(p1.getSalary() + p2.getSalary());
			            return p1;
			        });

			System.out.format("name=%s; age=%s", result.getAge(), result.getSalary()+"\n");
	}
	
	/*
	 * The third reduce method accepts three parameters: an identity value, a BiFunction accumulator and a 
	 * combiner function of type BinaryOperator. Since the identity values type is not restricted to the Employee type, 
	 * we can utilize this reduction to determine the sum of ages from all Employees
	 */
	private void reduceThirdWay() {
		Integer ageSum = employeeList
			    .stream()
			    .reduce(0, (sum, p) -> sum += p.getAge(), (sum1, sum2) -> sum1 + sum2);

			System.out.println("Combined age = "+ageSum);  
	}
	
	private void reduceThirdWay_CombinerNotCalled() {
		Integer ageSum = employeeList
			    .stream()
			    .reduce(0,                              // identity 
			    		(sum, p) -> {
			    			System.out.println("in aggregator");
			    	       return sum += p.getAge();    // accumulator
			    		},
					    (sum1, sum2) -> {
					    	System.out.println("in combiner");
					    	return sum1 + sum2;	        // combiner never called in single stream.
					    });			    
			    
       System.out.println("reduceThirdWay_CombinerNotCalled::age = " + ageSum);
	}

	private void reduceThirdWay_parallel() {
		Integer ageSum = employeeList
			    .parallelStream()
			    .reduce(0,                              // identity 
			    		(sum, p) -> {
			    			System.out.println("in aggregator");
			    	       return sum += p.getAge();    // accumulator
			    		},
					    (sum1, sum2) -> {
					    	System.out.println("in combiner");
					    	return sum1 + sum2;	        // combiner called only for parallel stream.
					    });			    
			    
       System.out.println("reduceThirdWay_parallel::age = " + ageSum);
	}
	
}
