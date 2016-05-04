package base;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTest {

	public volatile int a ;

	public AtomicInteger spm = new AtomicInteger(0);

	public int getA(){
		return  a+1;
	}

	public static void main(String[] args) {
		LockTest l = new LockTest();
		System.out.println(l.getA());
	}

}
