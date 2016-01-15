/**   
* @Title: ZooKeeperFactory.java 
* @Package zookeeper.lock.curator.locks 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午10:11:40 
* @version V1.0   
*/
package zookeeper.lock.curator.locks;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/** 
* @ClassName: ZooKeeperFactory 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月31日 上午10:11:40 
*/
public class ZooKeeperFactory {
    public static final String CONNECT_STRING = "192.168.34.92:2188";
    //public static final String CONNECT_STRING = "192.168.34.92:2181";
    public static final int MAX_RETRIES = 3;
 
    public static final int BASE_SLEEP_TIMEMS = 3000;
 
    public static final String NAME_SPACE = "cfg";
 
    public static CuratorFramework get() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIMEMS, MAX_RETRIES);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .retryPolicy(retryPolicy)
               // .namespace(NAME_SPACE)
                .build();
        client.start();
        return client;
    }
}
