package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * User: zhouq
 * Date: 2016/11/29
 */
public class RedisSampleLock {
    private static final String redisHost = "10.22.0.11";

    private static final int port = 6379;

    private static JedisPoolConfig config;

    private static JedisPool pool;

    private static ExecutorService service;

    private static int ThLeng = 10;

    private static CountDownLatch latch;

    private static AtomicInteger Countor = new AtomicInteger(0);

    private static int count = 0;

    private static String LockName = "mylock_test10";

    static {
        //利用Redis连接池，保证多个线程利用多个连接，充分模拟并发性
        config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxWait(1000);
        //config.setMaxTotal(30);
        pool = new JedisPool(config, redisHost, port);
        //利用ExecutorService 管理线程
        service = Executors.newFixedThreadPool(ThLeng);
        //CountDownLatch保证主线程在全部线程结束之后退出
        latch = new CountDownLatch(ThLeng);
    }

    /**
     * 獲取鎖
     * tips：生成一个UUID，作为Key的标识，不断轮询lockName，直到set成功，表示成功获取锁。
     * 其他的线程在set此lockName时被阻塞直到超时。
     *
     * @param pool
     * @param lockName
     * @param timeouts
     * @return 鎖標識
     */
    public static String getLock(JedisPool pool, String lockName, long timeouts) {
        Jedis client = pool.getResource();
        try {
            String value = UUID.randomUUID().toString();
            long timeWait = System.currentTimeMillis() + timeouts * 1000;
            while (System.currentTimeMillis() < timeWait) {
                if (client.setnx(lockName, value) == 1) {
                    System.out.println("lock geted");
                    return value;
                }
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("get lock timeouts");
        } finally {
            //pool.returnBrokenResource(client);
            pool.returnResource(client);
        }
        return null;
    }

    /**
     * 釋放鎖
     * tips：对lockName做watch，开启一个事务，删除以LockName为key的锁，删除后，此锁对于其他线程为可争抢的。
     *
     * @param pool
     * @param lockName
     * @param value
     */
    public static void relaseLock(JedisPool pool, String lockName, String value) {
        Jedis client = pool.getResource();
        try {
            while (true) {
                client.watch(lockName);
                if (client.get(lockName).equals(value)) {
                    Transaction tx = client.multi();
                    tx.del(lockName);
                    tx.exec();
                    return;
                }
                client.unwatch();
            }
        } finally {
            //pool.returnBrokenResource(client);
            pool.returnResource(client);
        }
    }


    public static void main(String args[]) {
        for (int i = 0; i < ThLeng; i++) {
            String tName = "thread-" + i;
            Thread t = new Thread(new SubAddThread(pool, tName));
            System.out.println(tName + "inited...");
            service.submit(t);
        }
        service.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Countor.get());
        System.out.println(count);
    }


    public static class SubAddThread implements Runnable {

        private String name;

        private JedisPool pool;

        public SubAddThread(JedisPool pool, String uname) {
            this.pool = pool;
            this.name = uname;
        }

        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(name + " starting...");
                String valuse = getLock(pool, LockName, 50);
                System.out.println(name + " get Lock " + valuse);
                count++;
                relaseLock(pool, LockName, valuse);
                Countor.incrementAndGet();
                System.out.println(name + " " + count);
            }
            latch.countDown();
            System.out.println(name + " complated");
        }

    }
}