/**   
* @Title: ZkLockClient.java 
* @Package zookeeper.lock.zkdemo.helper 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2016年1月22日 上午10:16:24 
* @version V1.0   
*/
package lock.zkdemo.helper;

import lock.zkdemo.utils.ZkClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

.lock.zkdemo.utils.ZkClientFactory;

/** 
* @ClassName: ZkLockClient 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2016年1月22日 上午10:16:24 
 */
public class ZkLockClient2 {

    private CuratorFramework client;
    private InterProcessMutex lock;

    public void setLock(String path) {
        client = ZkClientFactory.newClient();
        client.start();
        lock = new InterProcessMutex(client, path);
    }

    public boolean lock(long time) throws Exception {

        return lock.acquire(time, TimeUnit.SECONDS);
    }

    public void unlock(long time) throws Exception {
        lock.release();
    }
    
    public void close() {
        
    }
}
