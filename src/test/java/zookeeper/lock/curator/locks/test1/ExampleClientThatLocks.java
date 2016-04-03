/**   
* @Title: ExampleClientThatLocks.java 
* @Package zookeeper.lock.curator.locks.test1 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午11:41:16 
* @version V1.0   
*/
package zookeeper.lock.curator.locks.test1;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/** 
* @ClassName: ExampleClientThatLocks 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月31日 上午11:41:16 
*/
public class ExampleClientThatLocks {
    private final InterProcessMutex lock;
    private final FakeLimitedResource resource;
    private final String clientName;
    public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName)
    {
        this.resource = resource;
        this.clientName = clientName;
        lock = new InterProcessMutex(client, lockPath);
    }
    public void doWork(long time, TimeUnit unit) throws Exception
    {
        if (!lock.acquire(time, unit))
        {
            throw new IllegalStateException(clientName + " 不能得到互斥锁");
        }
        try
        {
            System.out.println(clientName + " 已获取到互斥锁");
            resource.use(); // 使用资源
            Thread.sleep(1000 * 1);
        }
        finally
        {
            System.out.println(clientName + " 释放互斥锁");
            lock.release(); // 总是在finally中释放
        }
    }
}
