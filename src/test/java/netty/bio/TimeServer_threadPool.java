package netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServer_threadPool {

	public static void main(String[] args) throws IOException {
		int port = 8899;
		ServerSocket server = null ;
		try {
			server = new ServerSocket(port);
			System.out.println("time server is start .... prot="+port);
			Socket socket = null;
			//线程池方式，避免线程过多，消耗资源
			ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
			while (true) {
				socket = server.accept();
				executor.execute(new TimeServerHandler(socket));
			}
		} catch (Exception e) {
			
		}finally{
			if (null != server) {
				server.close();
				server = null;
			}
		}
		
	}

}
