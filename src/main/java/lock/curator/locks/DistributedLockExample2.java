package lock.curator.locks;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实例
 * 
 * @author shencl
 */
public class DistributedLockExample2 {
    
   // String connectionString = "192.168.34.92:2181,192.168.34.92:2182,192.168.34.92:2183";
    static String connectionString = "192.168.34.92:2181";
    static ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
	private static CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
	private static final String PATH = "/zq111_locks";

	// 进程内部（可重入）读写锁

	static {
		
	}

	public static void main(String[] args) {
	    client.start();
	    try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
	    InterProcessMutex zqlock = new InterProcessMutex(client, PATH);
		try {

			for (int i = 0; i < 10; i++) {
				Thread t = new Thread(new MutexJob("Mutex任务" + i, zqlock));
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
		}
	}
	
	
	static class MutexJob implements Runnable {

	    private final String name;

	    private final InterProcessMutex lock;

	    // 锁等待时间
	    private final int wait_time = 10;

	    MutexJob(String name, InterProcessMutex lock) {
	        this.name = name;
	        this.lock = lock;
	    }

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
	            // 模拟job执行时间0-2000毫秒
	            int exeTime = new Random().nextInt(2000);
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
