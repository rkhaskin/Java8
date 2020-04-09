package com.funcinter.need;

//Java program to demonstrate need for Anonymous Inner class 
interface Age {
	int x = 21;

	void getAge();
}

//MyClass implement the methods of Age Interface 
class MyClass implements Age {
	@Override
	public void getAge() {
		// printing the age
		System.out.print("Age is " + x);
	}
}


public class CommonImpl {
	public static void main(String[] args) {
		// MyClass is implementation class of Age interface
		MyClass obj = new MyClass();

		// calling getage() method implemented at MyClass
		obj.getAge();
	}
}

