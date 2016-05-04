/**   
* @Title: LockStartup.java 
* @Package zookeeper.lock.curator.locks 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午10:10:00 
* @version V1.0   
*/
package lock.curator.locks;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/** 
* @ClassName: LockStartup 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月31日 上午10:10:00 
*/
public class LockStartup2 {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZooKeeperFactory.get(); // ZooKeeperFactory在上一篇文章《ZooKeeper学习笔记—配置管理》中有
        final InterProcessMutex mutex_lock = new InterProcessMutex(client, "/mutex_lock");
        
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new ParallelJob("Parallel任务" + i, mutex_lock));
            t.start();
        }
      
        client.close();
    }
     
    static class ParallelJob implements Runnable {

        private final String name;

        private final InterProcessMutex lock;

        // 锁等待时间
        private final int wait_time = 5;

        ParallelJob(String name, InterProcessMutex lock) {
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                doWork();
            } catch (Exception e) {
                // ingore;
            }
        }

        public void doWork() throws Exception {
            try {
                if (!lock.acquire(wait_time, TimeUnit.SECONDS)) {
                    System.err.println(name + "等待" + wait_time + "秒，仍未能获取到lock,准备放弃。");
                }
                // 模拟job执行时间0-3000毫秒
                int exeTime = new Random().nextInt(4000);
                System.out.println(name + "开始执行,预计执行时间= " + exeTime + "毫秒----------");
                Thread.sleep(exeTime);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.release();
            }
        } 
    }
}
