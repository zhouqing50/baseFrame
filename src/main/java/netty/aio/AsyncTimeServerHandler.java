package netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {

	private int port;
	private CountDownLatch latch;
	private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

	public AsyncTimeServerHandler(int port) {
		this.port = port;
		try {
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("timeServer is start :port="+port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

	}

	public void doAccept() {
		asynchronousServerSocketChannel.accept();
	}
}
