package juc.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {
	
	public static void main(String[] args) {
		ExecutorService executor =Executors.newFixedThreadPool(10);
		//1 runable
		try {
			Future<?> result = executor.submit(new Runnable() {
				public void run() {
				}
			});
			System.out.println("--runable--"+result.get());
		} catch (Exception e) {
		}
		
		//2 callable
		try {
			Future<Integer> result = executor.submit(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return 1;
				}
			});
			System.out.println("--callable--"+result.get());
		} catch (Exception e) {
		}
		
		//3 futureTask
		try {
			FutureTask<String> result = (FutureTask<String>) executor.submit(new Callable<String>() {

				public String call() throws Exception {
					return "futureTask....";
				}
			});
			System.out.println("--futureTask--"+result.get());
		} catch (Exception e) {
		}
	}
	
}
