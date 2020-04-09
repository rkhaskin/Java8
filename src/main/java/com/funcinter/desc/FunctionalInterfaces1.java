package com.funcinter.desc;

public class FunctionalInterfaces1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// Not functional because equals is already an implicit member (Object class)
	interface Foo {
		boolean equals(Object obj);
	}

	// Functional because Comparator has only one abstract non-Object method
	interface Comparator<T> {
		boolean equals(Object obj);

		int compare(T o1, T o2);
	}

	// Not functional because method Object.clone is not public
	interface Foo {
		int m();

		Object clone();
	}

	// Functional: two methods, but they have the same signature
	interface X {
		int m(Iterable<String> arg);
	}

	interface Y {
		int m(Iterable<String> arg);
	}

	interface Z extends X, Y {
	}

	interface X {
		Iterable m(Iterable<String> arg);
	}

	interface Y {
		Iterable<String> m(Iterable arg);
	}

	// Functional: Y.m is a subsignature & return-type-substitutable
	interface Z extends X, Y {
	}

	interface X {
		int m(Iterable<String> arg);
	}

	interface Y {
		int m(Iterable<Integer> arg);
	}

	// Not functional: No method has a subsignature of all abstract methods
	interface Z extends X, Y {
	}

	interface X {
		int m(Iterable<String> arg, Class c);
	}

	interface Y {
		int m(Iterable arg, Class<?> c);
	}

	// Not functional: No method has a subsignature of all abstract methods
	interface Z extends X, Y {
	}

	interface X {
		long m();
	}

	interface Y {
		int m();
	}

	// Compiler error: no method is return type substitutable
	interface Z extends X, Y {
	}

	interface Foo<T> {
		void m(T arg);
	}

	interface Bar<T> {
		void m(T arg);
	}

	// Compiler error: different signatures, same erasure
	interface FooBar<X, Y> extends Foo<X>, Bar<Y> {
	}
}
