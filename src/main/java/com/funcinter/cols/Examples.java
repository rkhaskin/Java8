package com.funcinter.cols;

import static com.funcinter.cols.Person.createPeople;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Stream;

// https://www.youtube.com/watch?v=pGroX3gmeP8

public class Examples {

	public static void main(String[] args) {
		Examples obj = new Examples();
		// without stream examples
		// obj.withoutStream();
		
		// stream examples
		obj.streamExamples();
	}
	
	private void withoutStream()
	{
		// print all the poeple in the collection
		createPeople().forEach(System.out::println); // eager evaluation
	}
	
	private void streamExamples()
	{
		// if I need to manipulate objects before final operation, use streams.
		// They are internal iterators. Besides iterator, give us lazy evaluation.
		createPeople()
		 .stream() // we are in the lazy evaluation
		 .filter(person -> person.getAge() > 30)
		 .map(Person::getName) // get name only
		 .forEach(name -> System.out.println(name)); // eager evaluation

		// get total age
		int totalAge = createPeople()
		 .stream() // we are in the lazy evaluation
		 .map(Person::getAge) // get age only
		 // returns total age of all people
		 // a)  reduce takes a collection and reduces it to a single value
		 // b)  reduce is a final operation
		 // .reduce(0, (total, age) -> total + age);
		 // .reduce(0, (total, age) -> Integer.sum(total, age));
		 .reduce(0, Integer::sum); // method reference
		
		System.out.println(totalAge);
		
		// get total age
				totalAge = createPeople()
				 .stream() // we are in the lazy evaluation
				 .mapToInt(Person::getAge) // get age only
				 .sum(); // when I use mapToInt, I can use sum instead of reduce
				
				System.out.println(totalAge);		
		/*
		 * There are two forms or reduce:
		 * 1) reduce - reduces to single value
		 * 2) collect - reduces to List, Set, Map, or any other structure.
		 */
		// -------------------------------
		// get the list of names in upper case
		
		List<String> namesOfPeopleOlderThan30 =
		createPeople()
		 .stream()
		 .filter(p -> p.getAge() > 30)
		 .map(Person::getName)
		 .map(String::toUpperCase)
		 // DO NOT DO THIS. It mutates an object which is shared with other threads (line 56)
		 // If we make the stream parallel, it will have race condition and objects will be lost (Shared mutability)
		 // .forEach(name -> namesOfPeopleOlderThan30.add(name));
		 
		 .reduce(
		     // first arg		 
			 new ArrayList<String>(), // initial value to start
			 // second arg
			 (names, name) -> {
				 names.add(name);  // this is internal mutability, the function is still pure
				 return names;
			 },
			 // third arg is used to deal with aggregating objects when the code is executed in parallel
			 (names1, names2) -> {
				 names1.addAll(names2); // this is internal mutability, the function is still pure
				 return names1;
			 }
		 );
		
		System.out.println(namesOfPeopleOlderThan30);
		
		// GOOD WAY to write the above logic
		namesOfPeopleOlderThan30 =
		createPeople()
		 .stream()
		 .filter(p -> p.getAge() > 30)
		 .map(Person::getName)
		 .map(String::toUpperCase)
		 // comes from Collectors. toList, toSet, toMap, etc are Collectors, which collect data properly, 
		 // making sure it is thread-safe to handle concurrency
		 .collect(toList());

		System.out.println(namesOfPeopleOlderThan30);
		
		// Create Map
//		System.out.println(
//		createPeople()
//		 .stream()
//		 // takes 2 params: key function, value function
//		 // .collect(toMap(person -> person.getName(), person -> person.getAge())));
//		 // this does not work with duplicate keys
//		 .collect(toMap(Person::getName, Person::getAge)));
		
		List<Integer> ages = createPeople()
				.stream()
				.map(Person::getAge)
				.collect(toList());
		
		// this is a modifiable list.
		ages.add(99);
		
		ages = createPeople()
				.stream()
				.map(Person::getAge)
				.collect(toUnmodifiableList()); // as of java 10
		
		// this will throw exception: UnsupportedOperation. cannot add to unmodifiable list
		// ages.add(99);
		
		
		// ----------------------------------
		// comma separated list
		String str = createPeople()
		.stream()
		.filter(person -> person.getAge() > 30)
		.map(Person::getName)
		.map(String::toUpperCase)
		.collect(joining(", "));
		
		System.out.println(str);
		
		
        // ----------------------------------------------
		// Split collection into two: those who have even number of age and those with odd number of age
		System.out.println(
		  createPeople()
		    .stream()
		    // Returns a Collector which partitions the input elements according to
		    // a Predicate, and organizes them into a Map<Boolean, List<T>>.
		    // The returned Map always contains mappings for both false and true keys.
		    // There are no guarantees on the type, mutability,serializability, or 
		    // thread-safety of the Map or List returned.
		    .collect(partitioningBy(person -> person.getAge() % 2 == 0))
		 );
          	
		
		// grouping: split collection into more then two
		Map<String, List<Person>> list = createPeople().stream()
				         // be default groupingBy returns List
				.collect(groupingBy(Person::getName));
		
		System.out.println(list);

		// create a map with key = name and value = List of ages 
		Map<String, List<Integer>> mapByName = createPeople().stream()
				// use name as a key                 // do map inside a collector to get age only
				                                     //and then send to list
				.collect(groupingBy(Person::getName, mapping(Person::getAge, toList())));
	 	              // collector  // function      // collector(Function, Collector)
		
		System.out.println(mapByName);

		// get set instead of a list
		Map<String, Set<Integer>> mapByNameSet = createPeople().stream()
				// use name as a key                 // do map inside a collector to get age only
				                                     //and then send to list
				.collect(groupingBy(Person::getName, mapping(Person::getAge, toSet())));
	 	              // collector  // function      // collector(Function, Collector)
		
		
		// count number of friends by name (Long)
		Map<String, Long> countByName = createPeople().stream()
				                                     // counting return Long
				.collect(groupingBy(Person::getName, counting()));
		
		System.out.println(countByName);
		
		// count number of friends by name (Integer)
		Map<String, Integer> countByNameInt = createPeople().stream()
				                                                // collect and then transform to int         
      	 .collect(groupingBy(Person::getName, collectingAndThen(counting(), Long::intValue)));
     	 // .collect(groupingBy(Person::getName, collectingAndThen(counting(), value -> value.intValue())));
		
		System.out.println(countByNameInt);
		

		// get max age
		OptionalInt res = createPeople().stream()
         .mapToInt(Person::getAge)
         .max();
		System.out.println(res);

		// get min age
		res = createPeople().stream()
         .mapToInt(Person::getAge)
         // works on int
         .min();
		
		System.out.println(res);
		
		// get sum age
		int val = createPeople().stream()
         .mapToInt(Person::getAge)
         // sum returns int, not OptionalInt. If the list is empty, the result is 0.
         .sum();

		System.out.println(res);		

		// get the oldest person
		Optional<Person> p = createPeople().stream()
				        // works on objects,  
				.collect(maxBy(
						// static method of Comparator
						comparing(Person::getAge)));
		
		System.out.println(p);

		// get the youngest person
		p = createPeople().stream()
				        // works on objects,
				.collect(minBy(
						// static method of Comparator
						comparing(Person::getAge)));
		
		System.out.println(p);

		// get the person's name, not the entire object
		createPeople().stream()
				        // works on objects,  
				.collect(
						// get the person with max age
						                  // collectingAndThen applies 1) collector
						collectingAndThen(maxBy(comparing(Person::getAge)),
								// 2) function
								// person is Optional, so use map to get value from Optional or else ""
								person -> person.map(Person::getName).orElse("")
								)
						);
		
		System.out.println(p);
		
		// create a map with key = name and value = List of ages
		Map<Integer, List<String>> res2 =
				createPeople().stream()
				         // hash key is age
				.collect(groupingBy(Person::getAge,
						    // value is list of names 
						    mapping(Person::getName,
						    		// only get names longer then 4
						    		filtering(name -> name.length() > 4,
						    		// return value is List<String>		
						    		toList()))));
		
		System.out.println(res2);
		
//		-----------------------------------
		
        Map<Integer, List<String>> mapOfStrings = createPeople().stream()
        .collect(
            // create Map with age as a key		
        	groupingBy(Person::getAge,
        		  // map thru person to get [ [a,b, c], [d, e, f], [, g, h, i] ] and flatten it to [a, b, c, d, e, f, g. h. i]	
        		  flatMapping(person -> Stream.of(person.getName().split("")),
        				  // return as list
        				  toList())));
        
        System.out.println(mapOfStrings);
        
        Map<Integer, Set<String>> mapOfStringsUpper = 
        createPeople().stream()
        .collect(
            // create Map with age as a key		
        	groupingBy(
        		 Person::getAge,
        		 // perform transformation
        		 mapping(
        		    // get name in upper case		 
        			person -> person.getName().toUpperCase(),
        			// transform to individual letters and put into Stream
        			flatMapping(name -> Stream.of(name.split("")),
        					// remove duplicate letters
        					toSet()))));
        
        System.out.println(mapOfStringsUpper);
        
        // sorting
        createPeople().stream()
          .sorted(comparing(Person::getAge).thenComparing(Person::getName)) 
          .forEach(System.out::println);
	}

}
