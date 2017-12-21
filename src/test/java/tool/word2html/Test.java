/**
 * 
 */
package tool.word2html;

/**
 * @author Frank
 *
 */

public class Test {
	int a;
	int b;

	public void f() {
		a = 0;
		b = 0;
		int[] c = { 0 };
		g(b, c);
		System.out.println(a + " " + b + " " + c[0] + " ");
	}

	public void g(int b, int[] c) {
		a = 1;
		b = 1;
		c[0] = 1;
	}

	public static void main(String[] args) {
		Test obj = new Test();
		obj.f();

		int i = 128;
		Integer i2 = 128;
		Integer i3 = new Integer(128);
		// Integer会自动拆箱为int，所以为true
		System.out.println(i3 == i2);
		System.out.println(i == i3);
		System.out.println("**************");
		Integer i5 = 127;// java在编译的时候,被翻译成-> Integer i5 = Integer.valueOf(127);
		Integer i6 = 127;
		System.out.println(i5 == i6);
		// true
		/*
		 * Integer i5 = 128; Integer i6 = 128; System.out.println(i5 ==
		 * i6);//false
		 */
		Integer ii5 = new Integer(127);
		System.out.println(i5 == ii5); // false
		Integer i7 = new Integer(1);
		Integer i8 = new Integer(1);
		System.out.println(i7 == i8); // false

		/*
		 * ArrayList<String> arr = new ArrayList<>(); arr.add("22"); arr.get(2);
		 * 
		 * String[] ss = new String[1]; ss[2] = "ss";
		 */
	}
}
