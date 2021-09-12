package com.funcinter.cols;

import java.util.List;

public class Person {
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	private Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public static List<Person> createPeople()
	{
		return List.of(
		  new Person("Sara", 20),		
		  new Person("Sara", 22),		
		  new Person("Bob", 20),		
		  new Person("Paula", 32),		
		  new Person("Paul", 32),		
		  new Person("Jack", 3),		
		  new Person("Jack", 72),		
		  new Person("Jill", 11)		
	    );
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}
