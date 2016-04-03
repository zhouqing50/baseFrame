/**   
* @Title: LockStartup.java 
* @Package zookeeper.lock.curator.locks 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午10:10:00 
* @version V1.0   
*/
package zookeeper.lock.curator.locks;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

/** 
* @ClassName: LockStartup 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月31日 上午10:10:00 
*/
public class LockStartup {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = ZooKeeperFactory.get(); // ZooKeeperFactory在上一篇文章《ZooKeeper学习笔记—配置管理》中有
        final InterProcessSemaphoreMutex processSemaphoreMutex = new InterProcessSemaphoreMutex(client, "/lockzzz");
        
        printProcess(processSemaphoreMutex);
         
        System.out.println("Starting get lock...");
        boolean flag = processSemaphoreMutex.acquire(12, TimeUnit.SECONDS);
        System.out.println(flag ? "Getting lock successful." : "Getting failed!");
         
        printProcess(processSemaphoreMutex);
         
        Thread.sleep(10 * 1000);
         
        if (processSemaphoreMutex.isAcquiredInThisProcess()) {
            processSemaphoreMutex.release();
        }
        printProcess(processSemaphoreMutex);
        client.close();
    }
     
    private static void printProcess(final InterProcessSemaphoreMutex processSemaphoreMutex) {
        // 在本进程中锁是否激活（是否正在执行）   
        System.out.println("isAcquiredInThisProcess: " + processSemaphoreMutex.isAcquiredInThisProcess());
    }
}
