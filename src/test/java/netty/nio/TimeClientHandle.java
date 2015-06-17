package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable{
	
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;
	private String host;
	private int port;
	
	public TimeClientHandle(String host, int port) {
		try {
			this.host = host;
			this.port = port;
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void run() {
		try {
			doConnect();
		} catch (Exception e) {
			// TODO: handle exception
		}
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if (null != key) {
							key.cancel();
							if (null != key.channel()) {
								key.channel().close();
							}
						}
					}finally{
						
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != selector) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void doConnect() throws IOException {
		if (socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
		
	}
	
	public void doWrite(SocketChannel sChannel) throws IOException {
		byte[] req = "get time order".getBytes("utf-8");
		ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
		byteBuffer.put(req);
		byteBuffer.flip();
		sChannel.write(byteBuffer);
		System.out.println("send order success!");
	/*	while (!byteBuffer.hasRemaining()) {
			System.out.println("send order success!");
		}*/
	}
	
	public void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			SocketChannel sChannel = (SocketChannel) key.channel();
			if (key.isConnectable()) {
				if (sChannel.finishConnect()) {
					System.out.println(33);
					sChannel.register(selector, SelectionKey.OP_READ);
					doWrite(sChannel);
				}else {
					System.exit(1);
				}
			}
			
			if (key.isReadable()) {
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readbytes = sChannel.read(readBuffer);
				if (readbytes > 0 ) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String resp = new String(bytes, "utf-8");
					System.out.println("Now is "+resp);
					this.stop = true;
				}else if (readbytes < 0) {
					key.cancel();
					sChannel.close();
				}
			}
		}
	}
}
