/**   
* @Title: ZkCientUtils.java 
* @Package zookeeper.lock.zkdemo.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2016年1月22日 上午11:18:51 
* @version V1.0   
*/
package zookeeper.lock.zkdemo.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/** 
* @ClassName: ZkCientUtils 
* lock使用案例
* InterProcessMutex lock = ZkCientUtils.setLock("/aaaa");
     try {
      if (lock.acquire(120, TimeUnit.SECONDS)) {
          try {
                  dosomething();
                } finally {
                    lock.release();
               }
    }
* 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2016年1月22日 上午11:18:51 
*/
public class ZkCientUtils {
    private static CuratorFramework client = null;
    //static String connectUrl = "192.168.34.92:2181,192.168.34.92:2182,192.168.34.92:2183";
    static String connectUrl = "192.168.34.92:2181";

    static {
        client = CuratorFrameworkFactory.newClient(connectUrl, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static InterProcessMutex setLock(String path) {
        return new InterProcessMutex(client, path);
    }

}
