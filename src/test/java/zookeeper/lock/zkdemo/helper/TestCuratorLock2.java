/**   
* @Title: TestCuratorLock.java 
* @Package zookeeper.lock.curator.locks 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午10:04:06 
* @version V1.0   
*/
package zookeeper.lock.zkdemo.helper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import zookeeper.lock.zkdemo.utils.ZkCientUtils;

public class TestCuratorLock2 {
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        CountDownLatch latch = new CountDownLatch(5);

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.submit(new MyLock("client" + i, latch));
        }
        exec.shutdown();
        latch.await();
        System.out.println("所有任务执行完毕");

        System.out.println("客户端关闭。。。。");
    }

    static class MyLock implements Runnable {
        private String name;
        private CountDownLatch latch;

        public MyLock(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            InterProcessMutex lock = ZkCientUtils.setLock("/aaaa");
            try {
                if (lock.acquire(120, TimeUnit.SECONDS)) {
                    try {
                        // do some work inside of the critical section here
                        System.out.println("----------" + this.name + "获得资源----------");
                        System.out.println("----------" + this.name + "正在处理资源----------");
                        Thread.sleep(10 * 1000);
                        System.out.println("----------" + this.name + "资源使用完毕----------");
                        latch.countDown();
                    } finally {
                        lock.release();
                        System.out.println("----------" + this.name + "释放----------");
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}