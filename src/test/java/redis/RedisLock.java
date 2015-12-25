/**   
* @Title: RedisLock.java 
* @Package redis 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月24日 上午11:14:26 
* @version V1.0   
*/
package redis;

import java.util.Random;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/** 
* @ClassName: RedisLock 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月24日 上午11:14:26 
*/
public class RedisLock {
    //加锁标志
    public static final String LOCKED = "TRUE";
    public static final long ONE_MILLI_NANOS = 10L;
    //默认超时时间（毫秒）
    public static final long DEFAULT_TIME_OUT = 1;
    public static JedisPool pool;
    public static final Random r = new Random();
    //锁的超时时间（秒），过期删除
    public static final int EXPIRE = 1;

    static {
        pool = new JedisPool(new Config(), "192.168.31.158", 6379);
    }

    private Jedis jedis;
    private String key;
    //锁状态标志
    private boolean locked = false;

    public RedisLock(String key) {
        this.key = key;
        this.jedis = pool.getResource();
    }

    public boolean lock(long timeout) {
        long nano = System.nanoTime();
        timeout *= ONE_MILLI_NANOS;
        try {
            while ((System.nanoTime() - nano) < timeout) {
                if (jedis.setnx(key, LOCKED) == 1) {
                    jedis.expire(key, EXPIRE);
                    locked = true;
                    return locked;
                }
                // 短暂休眠，nano避免出现活锁
                Thread.sleep(3, r.nextInt(500));
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean lock() {
        return lock(DEFAULT_TIME_OUT);
    }

    // 无论是否加锁成功，必须调用
    public void unlock() {
        try {
            if (locked)
                jedis.del(key);
        } finally {
            pool.returnResource(jedis);
        }
    }
}
