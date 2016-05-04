package base;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 假若有若干个线程都要进行写数据操作，并且只有所有线程都完成写数据操作之后，
 * 这些线程才能继续做后面的事情，此时就可以利用CyclicBarrier了：
 *
 * @author 青
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N, () -> System.out.println("当前线程" + Thread.currentThread().getName()));

        for (int i = 0; i < N; i++) {
            new Writer(barrier).start();
        }

  /*      List<Future<String>> results = new ArrayList<>();
        for (int j = 0; j < N; j++) {
            final int finalJ = j;
            results.add(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2).submit(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
                try {
                    Thread.sleep(5000);
                    System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("所有线程写入完毕，继续处理其他任务...");
                return "callable is " + finalJ;
            }));
        }

        results.forEach(a -> {
            try {
                System.out.println(a.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });*/
    }

    static class Writer extends Thread {

        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
