package frameWork;

public class SingltonClass {

	private static SingltonClass test;
	private static SingltonClass test1;
	
	public static SingltonClass method() {
		if(test==null) {
		test=	new SingltonClass();
		}
		return test;
	}
	
	public static void main(String[] args) {
System.out.println(SingltonClass.method());
System.out.println(SingltonClass.method1());
System.out.println(SingltonClass.method1());

	}	 
	public static SingltonClass method1() {
		if(test1==null) {
			test1=	new SingltonClass();
		}
		return test1;
	}
}
