
public class MyDataImpl implements MyData {

		public boolean isNull(String str) {
			System.out.println("Impl Null Check");

			return str == null ? true : false;
		}
		
		public static void main(String args[]){
			MyDataImpl obj = new MyDataImpl();
			obj.print("");
			obj.isNull("abc");
			
			int res = obj.count(6);
			System.out.println("res = " + res);
			
			// old way implementing interface abstract methods
			MyDataLambda lam = new MyDataLambda() {
                @Override
				public int countLambda(int num) {
					return num+1;
				}
			};
			
			System.out.println("old way " +lam.countLambda(8));
			
			

			// implement a function the new way. It implements the only abstract method on the interface.
			MyDataLambda objLambda = val -> ++val;
			int result = objLambda.countLambda(8);
			System.out.println("lambda res = " + result);
			
		}

		// implement a function the new way
		@Override
		public int count(int num) {
			return num++;
		}
		
		
	}

