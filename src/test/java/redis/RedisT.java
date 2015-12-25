/**   
* @Title: RedisT.java 
* @Package redis 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月24日 下午1:49:47 
* @version V1.0   
*/
package redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @ClassName: RedisT 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月24日 下午1:49:47 
*/
public class RedisT {
    
    public static void main(String[] args) {
        RedisT sRedisT = new RedisT();
        sRedisT.test();
    }

    public void test() {
        ExecutorService executor =Executors.newFixedThreadPool(20);
        final RedisLock redisLock = new  RedisLock("islock");
        
        JedisPool pool; 
          Jedis jedis; 
          
                pool = new JedisPool(new JedisPoolConfig(), "192.168.31.158");  
                jedis = pool.getResource(); 
                // jedis.auth("password");
                jedis.del("fts");
        for (int i = 0; i < 100; i++) {
           // executor.submit(new Task(redisLock, i, jedis));
            try {
                 if (redisLock.lock()) {
       
                     jedis.sadd("fts", "sucess-"+i);
                     System.out.println("------------"+i);
                     
                     System.out.println("--122-----"+jedis.smembers("fts"));
                  }
             } catch (Exception e) {
                 e.printStackTrace();
             }finally {
                 redisLock.unlock();
             }
        }
    }

    
    class Task extends Thread{
        
        private RedisLock redisLock;
        private int i;
        private Jedis jedis;
        
        public Task(RedisLock redisLock, int i, Jedis jedis) {
            this.redisLock = redisLock;
            this.i=i;
            this.jedis = jedis;
        }
        
        @Override
        public void run() {
            try {
               // if (redisLock.lock()) {
                    jedis.lpush("fts",  "sucess-"+i);
                    System.out.println("------------"+i);
                    
                    System.out.println(jedis.sort("fts"));
                // }
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                //redisLock.unlock();
            }
           
        }
    }
    
}
