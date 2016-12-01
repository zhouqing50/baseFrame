package redis;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisTest {

    JedisPool pool;
    Jedis jedis;

    @Before
    public void setUp() {
        pool = new JedisPool(new JedisPoolConfig(), "10.22.0.11");
        jedis = pool.getResource();
        // jedis.auth("password");
    }

    /**
     * Redis存储初级的字符串 * CRUD
     * set key value 设置key对应的值为string类型的value,返回1表示成功，0失败
     * setnx key value 同上，如果key已经存在，返回0 。nx 是not exist的意思
     * get key 获取key对应的string值,如果key不存在返回nil
     * getset key value 原子的设置key的值，并返回key的旧值。如果key不存在返回nil
     * mget key1 key2 ... keyN 一次获取多个key的值，如果对应key不存在，则对应返回nil。下面是个实验,首先清空当前数据库，然后 设置k1,k2.获取时k3对应返回nil
     */
    @Ignore
    public void testBasicString() {
        //-----添加数据----------
        jedis.set("name", "minxr");//向key-->name中放入了value-->minxr
        System.out.println(jedis.get("name"));//执行结果：minxr //-----修改数据-----------
        //1、在原来基础上修改
        jedis.append("name", "jarorwar");   //很直观，类似map 将jarorwar append到已经有的value之后
        System.out.println(jedis.get("name"));//执行结果:minxrjarorwar
        //2、直接覆盖原来的数据
        jedis.set("name", "闵晓荣");
        System.out.println(jedis.get("name"));//执行结果：闵晓荣
        jedis.expire("name", 1); //设置有效期为1秒
        //删除key对应的记录
        jedis.del("name");
        System.out.println(jedis.get("name"));//执行结果：null
        /** * mset相当于 * jedis.set("name","minxr"); * jedis.set("jarorwar","闵晓荣"); */
        jedis.mset("name", "minxr", "jarorwar", "闵晓荣");
        System.out.println(jedis.mget("name", "jarorwar"));
    }

    /**
     * jedis操作Map
     * redis hash是一个string类型的field和value的映射表.它的添加，删除操作都是O(1)（平均）.hash特别适合用于存储对象。相较于将对象的每个字段存成
     * 单个string类型。将一个对象存储在hash类型中会占用更少的内存，并且可以更方便的存取整个对象。省内存的原因是新建一个hash对象时开始是用zipmap（又称为small hash）来存储的。这个zipmap其实并不是hash table，但是zipmap相比正常的hash实现可以节省不少hash本身需要的一些元数据存储开销。尽管zipmap的添加，删除，查找都是O(n)，但是由于一般对象的field数量都不太多。所以使用zipmap也是很快的,也就是说添加删除平均还是O(1)。如果field或者value的大小超出一定限制后，redis会在内部自动将zipmap替换成正常的hash实现. 这个限制可以在配置文件中指定
     * hash-max-zipmap-entries 64 #配置字段最多64个
     * hash-max-zipmap-value 512 #配置value最大为512字节
     * <p/>
     * hset key field value 设置hash field为指定值，如果key不存在，则先创建
     * hget key field  获取指定的hash field
     * hmget key filed1....fieldN 获取全部指定的hash filed
     * hmset key filed1 value1 ... filedN valueN 同时设置hash的多个field
     * hincrby key field integer 将指定的hash filed 加上给定值
     * hexists key field 测试指定field是否存在
     * hdel key field 删除指定的hash field
     * hlen key 返回指定hash的field数量
     * hkeys key 返回hash的所有field
     * hvals key 返回hash的所有value
     * hgetall 返回hash的所有filed和value
     */
    @Test
    public void testMap() {
        Map<String, String> user = new HashMap<>();
        user.put("name", "minxr");
        user.put("pwd", "password");
        jedis.hmset("user", user);
        jedis.hset("user", "age", "28");
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        List<String> rsmap = jedis.hmget("user", "name");
        System.out.println(rsmap);  //删除map中的某个键值 //
        jedis.hdel("user", "pwd");
        System.out.println(jedis.hmget("user", "pwd")); //因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数1
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key  [pwd, name]
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value  [minxr, password]
        System.out.println(jedis.hgetAll("user"));//返回hash的所有filed和value
        for (String key : jedis.hkeys("user")) {
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
    }

    /**
     * jedis操作List
     * redis的list类型其实就是一个每个子元素都是string类型的双向链表。所以[lr]push和[lr]pop命令的算法时间复杂度都是O(1)
     * 另外list会记录链表的长度。所以llen操作也是O(1).链表的最大长度是(2的32次方-1)。我们可以通过push,pop操作从链表的头部
     * 或者尾部添加删除元素。这使得list既可以用作栈，也可以用作队列。有意思的是list的pop操作还有阻塞版本的。当我们[lr]pop一个
     * list对象是，如果list是空，或者不存在，会立即返回nil。但是阻塞版本的b[lr]pop可以则可以阻塞，当然可以加超时时间，超时后也会返回nil
     * 。为什么要阻塞版本的pop呢，主要是为了避免轮询。举个简单的例子如果我们用list来实现一个工作队列。执行任务的thread可以调用阻塞版本的pop去
     * 获取任务这样就可以避免轮询去检查是否有任务存在。当任务来时候工作线程可以立即返回，也可以避免轮询带来的延迟。ok下面介绍list相关命令
     *
     * lpush key string 在key对应list的头部添加字符串元素，返回1表示成功，0表示key存在且不是list类型
     * rpush key string 同上，在尾部添加
     * llen key 返回key对应list的长度，key不存在返回0,如果key对应类型不是list返回错误
     * lrange key start end 返回指定区间内的元素，下标从0开始，负值表示从后面计算，-1表示倒数第一个元素 ，key不存在返回空列表
     * ltrim key start end  截取list，保留指定区间内元素，成功返回1，key不存在返回错误
     * lset key index value 设置list中指定下标的元素值，成功返回1，key或者下标不存在返回错误
     * lrem key count value 从key对应list中删除count个和value相同的元素。count为0时候删除全部
     * lpop key 从list的头部删除元素，并返回删除元素。如果key对应list不存在或者是空返回nil，如果key对应值不是list返回错误
     * rpop 同上，但是从尾部删除
     * blpop key1...keyN timeout 从左到右扫描返回对第一个非空list进行lpop操作并返回，比如blpop list1 list2 list3 0 ,如果list不存在
     * list2,list3都是非空则对list2做lpop并返回从list2中删除的元素。如果所有的list都是空或不存在，则会阻塞timeout秒，timeout为0表示一直阻塞。
     * 当阻塞时，如果有client对key1...keyN中的任意key进行push操作，则第一在这个key上被阻塞的client会立即返回。如果超时发生，则返回nil。有点像unix的select或者poll
     * brpop 同blpop，一个是从头部删除一个是从尾部删除
     */
    @Test
    public void testList() {
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1)); //先向key java framework中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate"); //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    /**
     * jedis操作Set
     * sadd key member 添加一个string元素到,key对应的set集合中，成功返回1,如果元素以及在集合中返回0,key对应的set不存在返回错误
     * srem key member 从key对应set中移除给定元素，成功返回1，如果member在集合中不存在或者key不存在返回0，如果key对应的不是set类型的值返回错误
     * spop key 删除并返回key对应set中随机的一个元素,如果set是空或者key不存在返回nil
     * srandmember key 同spop，随机取set中的一个元素，但是不删除元素
     * smove srckey dstkey member 从srckey对应set中移除member并添加到dstkey对应set中，整个操作是原子的。成功返回1,如果member在srckey中不存在返回0，如果
     * key不是set类型返回错误
     * scard key 返回set的元素个数，如果set是空或者key不存在返回0
     * sismember key member 判断member是否在set中，存在返回1，0表示不存在或者key不存在
     * sinter key1 key2...keyN 返回所有给定key的交集
     * sinterstore dstkey key1...keyN 同sinter，但是会同时将交集存到dstkey下
     * sunion key1 key2...keyN 返回所有给定key的并集
     * sunionstore dstkey key1...keyN 同sunion，并同时保存并集到dstkey下
     * sdiff key1 key2...keyN 返回所有给定key的差集
     * sdiffstore dstkey key1...keyN 同sdiff，并同时保存差集到dstkey下
     * smembers key 返回key对应set的所有元素，结果是无序的
     */
    @Ignore
    public void testSet() {
        //添加
        jedis.sadd("sname", "minxr");
        jedis.sadd("sname", "jarorwar");
        jedis.sadd("sname", "闵晓荣");
        jedis.sadd("sanme", "noname");
        //移除noname
        jedis.srem("sname", "noname");
        System.out.println(jedis.smembers("sname"));//获取所有加入的value
        System.out.println(jedis.sismember("sname", "minxr"));//判断 minxr 是否是sname集合的元素
        System.out.println(jedis.srandmember("sname"));
        System.out.println(jedis.scard("sname"));//返回集合的元素个数

    }

//    public void test() throws InterruptedException {
//
//        //keys中传入的可以用通配符
//        System.out.println(jedis.keys("*")); //返回当前库中所有的key  [sose, sanme, name, jarorwar, foo, sname, java framework, user, braand]
//        System.out.println(jedis.keys("*name"));//返回的sname   [sname, name]
//        System.out.println(jedis.del("sanmdde"));//删除key为sanmdde的对象  删除成功返回1 删除失败（或者不存在）返回 0
//        System.out.println(jedis.ttl("sname"));//返回给定key的有效时间，如果是-1则表示永远有效
//        jedis.setex("timekey", 10, "min");//通过此方法，可以指定key的存活（有效时间） 时间为秒
//        Thread.sleep(5000);//睡眠5秒后，剩余时间将为<=5
//        System.out.println(jedis.ttl("timekey"));   //输出结果为5
//        jedis.setex("timekey", 1, "min");        //设为1后，下面再看剩余时间就是1了
//        System.out.println(jedis.ttl("timekey"));  //输出结果为1
//        System.out.println(jedis.exists("key"));//检查key是否存在
//        System.out.println(jedis.rename("timekey", "time"));
//        System.out.println(jedis.get("timekey"));//因为移除，返回为null
//        System.out.println(jedis.get("time"));
//        //因为将timekey 重命名为time 所以可以取得值 min //jedis 排序 //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
//        jedis.del("a");//先清除数据，再加入数据进行测试
//        jedis.rpush("a", "1");
//        jedis.lpush("a", "6");
//        jedis.lpush("a", "3");
//        jedis.lpush("a", "9");
//        System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
//        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
//        System.out.println(jedis.lrange("a", 0, -1));
//
//    }
}

