package com.funcinter.lookup;

import java.util.Optional;

class Outer {
    Nested nested;
}

class Nested {
    Inner inner;
}

class Inner {
    String foo;
}

public class FlatMapOptional {

	public static void main(String[] args) {
		FlatMapOptional obj = new FlatMapOptional();
		obj.oldway();
		obj.newWay();
	}

	private void oldway() {
		Outer outer = new Outer();
		if (outer != null && outer.nested != null && outer.nested.inner != null) {
		    System.out.println(outer.nested.inner.foo);
		}
	}
	
	private void newWay() {
		Optional.of(new Outer())
	    .flatMap(o -> Optional.ofNullable(o.nested)) // takes in Outer returns Nested
	    .flatMap(n -> Optional.ofNullable(n.inner))  // takes in Nested returns Inner
	    .flatMap(i -> Optional.ofNullable(i.foo))    // takes in Inner returns Inner.foo
	    .ifPresent(System.out::println);		
	}
}
