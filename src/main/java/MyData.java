
public interface MyData {

		default void print(String str) {
			if (!isNull(str))
				System.out.println("MyData Print::" + str);
		}

		// is visible to this interface methods only!
		static boolean isNull(String str) {
			System.out.println("Interface Null Check");

			return str == null ? true : "".equals(str) ? true : false;
		}
		
	public int count(int size);
}