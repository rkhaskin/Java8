
// implement a function the new way. It implements the only abstract method on the interface.
public class MyDataLambdaImpl {

	public static void main(String args[]) {
		MyDataLambda objLambda = val -> ++val;
		int result = objLambda.countLambda(8);
		System.out.println("lambda res = " + result);

	}
}
