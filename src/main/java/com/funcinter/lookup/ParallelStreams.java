package com.funcinter.lookup;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.funcinter.model.Employee;


/*
 * Streams can be executed in parallel to increase runtime performance on large amount of input elements. 
 * Parallel streams use a common ForkJoinPool available via the static ForkJoinPool.commonPool() method. 
 * The size of the underlying thread-pool uses up to five threads - 
 * depending on the amount of available physical CPU cores:
 */
public class ParallelStreams {

	
	
	static Employee[] employeeArray = new Employee[] {
			new Employee("Tom Jones", 45, 7000.00),
			new Employee("Harry Major", 25, 110000.00),
			new Employee("Ethan Hardy", 45, 8000.00),
			new Employee("Nancy Smith", 22, 12000.00),
			new Employee("Deborah Sprightly", 22, 9000.00)
	};
	
	public static void main(String[] args) {
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		System.out.println(commonPool.getParallelism());    // default based on numbers of cpu
		
		// to change the default: -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
		
		ParallelStreams obj = new ParallelStreams();
		obj.parallel1();
		obj.parallelWithSort();
		obj.parallelReducer();
		obj.parallelReducerRaw();

		
	}

	private void parallel1() {
		System.out.println("parallel1");
		Arrays.asList("a1", "a2", "b1", "c2", "c1")
	    .parallelStream()
	    .filter(s -> {
	        System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
	        return true;
	    })
	    .map(s -> {
	        System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
	}
	
	private void parallelWithSort() {
		System.out.println("parallelWithSort");
		Arrays.asList("a1", "a2", "b1", "c2", "c1")
	    .parallelStream()
	    .filter(s -> {
	        System.out.format("filter: %s [%s]\n",
	            s, Thread.currentThread().getName());
	        return true;
	    })
	    .map(s -> {
	        System.out.format("map: %s [%s]\n",
	            s, Thread.currentThread().getName());
	        return s.toUpperCase();
	    })
	    .sorted((s1, s2) -> {
	        System.out.format("sort: %s <> %s [%s]\n",
	            s1, s2, Thread.currentThread().getName());
	        return s1.compareTo(s2);
	    })
	    .forEach(s -> System.out.format("forEach: %s [%s]\n",
	        s, Thread.currentThread().getName()));
	}
	
	private void parallelReducer() {
        System.out.println("parallelReducer");
		BiFunction<Double, Employee, Double> accumulator = (sum, p) -> {
            System.out.format("accumulator: sum=%s; person=%s [%s]\n", sum, p, Thread.currentThread().getName());
            return sum += p.getAge();
        };
        
        BinaryOperator<Double> combiner =  (sum1, sum2) -> {
            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
	                sum1, sum2, Thread.currentThread().getName());
	            return sum1 + sum2;
	        };

		
		Stream.of(employeeArray)
			    .parallel()
			    .reduce(0.0,
			    	accumulator,
			    	combiner);
	}
	
	private void parallelReducerRaw() {
		System.out.println("parallelReducerRaw");
		Stream.of(employeeArray)
	    .parallel()
			    .reduce(0.0, // the type of the identity must match the sum type
			        (sum, p) -> {
			            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
			                sum, p, Thread.currentThread().getName());
			            return sum += p.getSalary();
			        },
			        (sum1, sum2) -> {
			            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
			                sum1, sum2, Thread.currentThread().getName());
			            return sum1 + sum2;
			        });
	}


    

}
