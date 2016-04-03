/**   
* @Title: InterProcessMutexExample.java 
* @Package zookeeper.lock.curator.locks.test1 
* @Description: TODO(用一句话描述该文件做什么) 
* @Company:彩讯科技
* @author 青  
* @date 2015年12月31日 上午11:41:53 
* @version V1.0   
*/
package zookeeper.lock.curator.locks.test1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/** 
* @ClassName: InterProcessMutexExample 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @Company:彩讯科技
* @author 青 
* @version 1.0 2015年12月31日 上午11:41:53 
*/
public class InterProcessMutexExample {
    private static final int QTY = 5;
    private static final int REPETITIONS = QTY * 10;
    private static final String PATH = "/azz/locks";
    public static void main(String[] args) throws Exception
    {
        final FakeLimitedResource resource = new FakeLimitedResource();
        final List<CuratorFramework> clientList = new ArrayList<CuratorFramework>();
        for (int i = 0; i < QTY; i++)
        {
            CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.34.92:2188", new ExponentialBackoffRetry(1000, 3));
            client.start();
            clientList.add(client);
        }
        System.out.println("连接初始化完成！");
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        for (int i = 0; i < QTY; ++i)
        {
            final int index = i;
            Callable<Void> task = new Callable<Void>()
            {
                @Override
                public Void call() throws Exception
                {
                    try
                    {
                        final ExampleClientThatLocks example = new ExampleClientThatLocks(clientList.get(index), PATH, resource, "Client " + index);
                        for (int j = 0; j < REPETITIONS; ++j)
                        {
                            example.doWork(10, TimeUnit.SECONDS);
                        }
                    }
                    catch (Throwable e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        CloseableUtils.closeQuietly(clientList.get(index));
                    }
                    return null;
                }
            };
            service.submit(task);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("OK!");
    }
}
