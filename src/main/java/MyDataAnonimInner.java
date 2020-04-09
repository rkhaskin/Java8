
// old way implementing interface abstract methods
public class MyDataAnonimInner {

	public static void main(String args[]) {
		MyDataLambda lam = new MyDataLambda() {
			@Override
			public int countLambda(int num) {
				return num + 1;
			}
		};

		int res = lam.countLambda(8);
		System.out.println("old way " + res);
	}

}
