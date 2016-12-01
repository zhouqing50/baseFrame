//package redis;
//
//import org.junit.Before;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * Description:
// * User: zhouq
// * Date: 2016/11/29
// */
//public class AredisLock {
//    JedisPool pool;
//    Jedis jedis;
//
//    @Before
//    public void setUp() {
//        pool = new JedisPool(new JedisPoolConfig(), "10.22.0.11");
//        jedis = pool.getResource();
//        // jedis.auth("password");
//    }
//
//
//    private boolean tryLock(final String lock, String value, int duration) {
//        Long setResult = jedis.setnx(lock, value);
//        System.out.println("tryLock lock="+lock+"result="+setResult);
//        return setResult != null && "OK".equalsIgnoreCase(setResult);
//    }
//
//    private boolean unlock(final String lock, String value) {
//        final String lockValue = jedis.get(lock);
//        if (lockValue != null && value.equals(lockValue)) {
//            jedis.del(lock);
//            return true;
//        }
//        return false;
//    }
//}
