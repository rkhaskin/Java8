package com.funcinter.lookup;

import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Stream;

import com.funcinter.model.Employee;

public class CustomCollector {

	static Employee[] employeeArray = new Employee[] {
			new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00),
			new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00),
			new Employee("Deborah Sprightly", 22, 9000.00)
	};
	
	public static void main(String[] args) {
		CustomCollector obj = new CustomCollector();
		obj.useCustomCollector();
	}

	/*
	 * build names separated by pipes: // MAX | PETER | PAMELA | DAVID
	 */
	private void useCustomCollector() {
		Collector<Employee, StringJoiner, String> personNameCollector =
			    Collector.of(
			        () -> new StringJoiner(" | "),                // joiner - Supplier
			        (j, p) -> j.add(p.getName().toUpperCase()),   // accumulator - BiConsumer
			        (j1, j2) -> j1.merge(j2),                     // combiner - merge two joiners
			        StringJoiner::toString);                      // finisher - return final result

			String names = Stream.of(employeeArray)
			    .collect(personNameCollector);

			System.out.println(names);  // MAX | PETER | PAMELA | DAVID		
	}

    

}
