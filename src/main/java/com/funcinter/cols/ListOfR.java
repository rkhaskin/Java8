package com.funcinter.cols;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListOfR {

	public static void main(String[] args) {
		List<Integer> numbers = List.of(1, 2, 3);
		
		// one-to-one function [2, 4, 6]
		List<Integer> list1 = 
		  numbers.stream()
		    .map(e -> e * 2) // one value in -> one value out: Stream<R>: 
		    .collect(Collectors.toList());
		
		System.out.println(list1);
		
		// one-to-many function  [[2, 0], [3, 1], [4, 2]]
		List<List<Integer>> list = numbers.stream()
		.map(e -> List.of(e + 1, e - 1)) // one value in -> List out
		.collect(Collectors.toList());
		
		System.out.println(list);

		// one-to-many function  [2, 0, 3, 1, 4, 2]
		List<Integer> list3 = numbers.stream()
		.flatMap(e -> List.of(e + 1, e - 1)
				.stream()) // create  a stream
		.collect(Collectors.toList());
		
		System.out.println(list3);

		// one-to-many function [0, 1, 2, 3, 4]
		Set<Integer> list4 = numbers.stream()
		.flatMap(e -> List.of(e + 1, e - 1)
				.stream()) // create  a stream
		.collect(Collectors.toSet()); // eliminate duplicates
		
		System.out.println(list4);
		
		/*
		Stream
		 .map(Function<T, R>) ===> Stream<R>
		
		    // the function return value can be list, set, anything
		    // that is Iterator
		    // flatMap takes Stream<List<R>> and returns Stream<R>
		    // flatMap wants a structure that has an Iterator<R>  
		 .flatMap(Function<T, Iterator<R>> ===> Stream<R>
		
		if I have one-to-one function, go to Stream<T> to Stream<R>
		if I have one-to-many function, use map to go from Stream<T> to Stream<Collection<R>>
		   or flatMap to go from Stream<T> to Stream<R>
		*
		*
		*/
		
		 
	}

}
