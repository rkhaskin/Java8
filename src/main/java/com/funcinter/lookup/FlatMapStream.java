package com.funcinter.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.funcinter.model.Bar;
import com.funcinter.model.Foo;

/*
 * FlatMap
 * Map is kind of limited because every object can only be mapped to exactly one other object. But what if we want to transform 
 * one object into multiple others or none at all? This is where flatMap comes to the rescue.
 * FlatMap transforms each element of the stream into a stream of other objects. So each object will be transformed into zero, 
 * one or multiple other objects backed by streams. The contents of those streams will then be placed into the 
 * returned stream of the flatMap operation.
  */
public class FlatMapStream {

	public static void main(String[] args) {
		FlatMapStream obj = new FlatMapStream();
		obj.transform1();		
		obj.transform2();
		
	}

	/*
	 * 	build a list of three foos each consisting of three bars.

	 */
	private void transform1() {
		List<Foo> foos = new ArrayList<>();

		// create List(3) foos
		IntStream
		    .range(1, 4)
		    .forEach(i -> foos.add(new Foo("Foo" + i)));

		// create list(3) of bars for each Foo 
		foos.forEach(f ->
		    IntStream
		        .range(1, 4)
		        .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));
		
		// transform a stream of 3 foo objects into a stream of 9 bar objects
		foos.stream()
	      .flatMap(f -> f.bars.stream())
    	  .forEach(b -> System.out.println(b.name));
	}
	
	/*
	 * same as above
	 */
	private void transform2() {
		IntStream.range(1, 4)
	    .mapToObj(i -> new Foo("Foo" + i))
	    .peek(f -> IntStream.range(1, 4).mapToObj(i -> new Bar("Bar" + i + " <- " + f.name)).forEach(f.bars::add))
	    .flatMap(f -> f.bars.stream())
	    .forEach(b -> System.out.println(b.name));
	}
}
