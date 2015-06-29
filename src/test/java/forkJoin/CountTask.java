package forkJoin;



import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class CountTask extends RecursiveTask<Integer>{

	private static final long serialVersionUID = 1L;
	
	private volatile AtomicInteger  aa;
	
	private static final int THRESHOLD = 5;
	private int start;
	private int end;
	
	public  CountTask(int start, int end, AtomicInteger aa) {
		this.start = start;
		this.end = end;
		this.aa = aa;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		boolean canCompute = (end - start) <= THRESHOLD ;
		
/*		aa.incrementAndGet();
		System.out.println( aa +"------"+sum );*/
		
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
				aa.incrementAndGet();
				System.out.println( "第"+aa +"次运算结果为:"+sum );
			}
		}else {
			int middle = (end + start)/2;
			CountTask countTask1 = new CountTask(start, middle, aa);
			CountTask countTask2 = new CountTask(middle+1, end,aa);
			countTask1.fork();
			countTask2.fork();
			int count1 = countTask1.join();
			int count2 = countTask2.join();
			sum = count1 + count2;
		}
		return sum;
	}

	public static void main(String[] args) {
			ForkJoinPool pool = new ForkJoinPool();
			ForkJoinTask<Integer> result = pool.submit(new CountTask(1,20,new AtomicInteger(0)));
			try {
				System.out.println(result.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
	}
}
