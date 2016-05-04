package base;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) {

        final CountDownLatch latch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            ((Runnable) () -> {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).run();
        }

        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
