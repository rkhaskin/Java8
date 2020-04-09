package com.funcinter.lookup;

import java.util.function.Supplier;
import java.util.stream.Stream;

import com.funcinter.model.Employee;

public class StreamReuse {

	static Employee[] employeeArray = new Employee[] { new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00), new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00), new Employee("Deborah Sprightly", 22, 9000.00) };

	public static void main(String[] args) {
		StreamReuse obj = new StreamReuse();
		obj.correctReuse();
		obj.reuseError();
	}

	private void reuseError() {
		System.out.println("(reuseError)");
		Stream<String> stream = Stream
				.of("d2", "a2", "b1", "b3", "c")
				.filter(s -> s.startsWith("a"));

		stream.anyMatch(s -> true); // ok
		try {
		   stream.noneMatch(s -> true); // exception
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void correctReuse() {
		System.out.println("(correctReuse)");
		//create a stream supplier to construct a new stream with all intermediate operations already set up.
		Supplier<Stream<String>> streamSupplier = () -> Stream
				.of("d2", "a2", "b1", "b3", "c")
				.filter(s -> s.startsWith("a"));
		//Each call to get() constructs a new stream on which we are save to call the desired terminal operation.
		streamSupplier.get().anyMatch(s -> true);   // ok
		streamSupplier.get().noneMatch(s -> true);  // ok
	}
}
