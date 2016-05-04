/**   
* @Title: FakeLimitedResource.java 
* @Package zookeeper.lock.curator.locks.test1 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午11:40:37 
* @version V1.0   
*/
package lock.curator.locks.test1;

import java.util.concurrent.atomic.AtomicBoolean;

/** 
* @ClassName: FakeLimitedResource 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月31日 上午11:40:37 
*/
public class FakeLimitedResource {
    private final AtomicBoolean inUse = new AtomicBoolean(false);
    // 模拟只能单线程操作的资源
    public void use() throws InterruptedException
    {
        if (!inUse.compareAndSet(false, true))
        {
            // 在正确使用锁的情况下，此异常不可能抛出
            throw new IllegalStateException("Needs to be used by one client at a time");
        }
        try
        {
            Thread.sleep((long) (3 * Math.random()));
        }
        finally
        {
            inUse.set(false);
        }
    }
}
