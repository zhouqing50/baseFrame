package base;

import java.util.concurrent.*;

public class FutureTest {
	
	public static void main(String[] args) {
		ExecutorService executor =Executors.newFixedThreadPool(10);
		//1 runable
		try {
			Future<?> result = executor.submit((Runnable) () -> {
            });
			System.out.println("--runable--"+result.get());
		} catch (Exception e) {
		}
		
		//2 callable
		try {
			Future<Integer> result = executor.submit(() -> 1);
			System.out.println("--callable--"+result.get());
		} catch (Exception e) {
		}
		
		//3 futureTask
		try {
			FutureTask<String> result = (FutureTask<String>) executor.submit(() -> "futureTask....");
			System.out.println("--futureTask--"+result.get());
		} catch (Exception e) {
		}
	}
	
}
