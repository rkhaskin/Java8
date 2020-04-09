package com.funcinter.need;

//Java program to demonstrate Anonymous inner class 
interface Age2 
{ 
	int x = 21; 
	void getAge(); 
}

class AnonimImpl 
{ 
	public static void main(String[] args) { 

		// Myclass is hidden inner class of Age interface 
		// whose name is not written but an object to it 
		// is created. 
		Age2 oj1 = new Age2() { 

			@Override
			public void getAge() { 
				System.out.print("Age is "+x); 
			} 
		}; 

		oj1.getAge(); 
	} 
} 
