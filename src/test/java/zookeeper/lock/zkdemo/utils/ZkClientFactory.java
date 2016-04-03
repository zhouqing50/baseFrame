package zookeeper.lock.zkdemo.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkClientFactory {

	public static CuratorFramework newClient() {
	    
	    String connectUrl = "192.168.34.92:2181,192.168.34.92:2182,192.168.34.92:2183";
	    //String connectionString = "192.168.34.92:2181";
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		return CuratorFrameworkFactory.newClient(connectUrl, retryPolicy);
	}
	
	
}
