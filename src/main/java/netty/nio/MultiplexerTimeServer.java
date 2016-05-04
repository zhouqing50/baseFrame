package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private volatile boolean stop;
	
	private String ORDER = "get time order";
	
	public MultiplexerTimeServer(int port) throws IOException {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("time server is start .... prot="+port);
	}
	
	public void run() {
		// 轮询访问selector
		while (!stop) {
			try {
				//当注册的事件到达时，方法返回；否则,该方法会一直阻塞,超过1s
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();// 删除已选的key,以防重复处理 
					try {
						handleInput(key);
					} catch (Exception e) {
						if (null != key) {
							key.cancel();
						}
						if (null != key.channel()) {
							key.channel().close();
						}
					}
				}
			} catch (Exception e) {
				
			}finally{
				
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
	
	public void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			if (key.isAcceptable()) {
				ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
				// 获得和客户端连接的通道 
				SocketChannel sChannel = ssChannel.accept();
				sChannel.configureBlocking(false);
				sChannel.register(selector, SelectionKey.OP_READ);
			}
			
			if (key.isReadable()) {
				SocketChannel socketChannel = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = socketChannel.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"utf-8");
					System.out.println(body);
					String currentTime = ORDER.equals(body)?new Date(System.currentTimeMillis()).toString():"bad order!";
					doWrite(socketChannel, currentTime);
				}else if (readBytes < 0) {
					key.cancel();
					socketChannel.close();
				}
				
			}
		}
	}
	
	public void doWrite(SocketChannel socketChannel, String resp) throws IOException {
		if (null != resp && resp.trim().length() > 0) {
			byte[] rep = resp.getBytes("utf-8");
			ByteBuffer byteBuffer = ByteBuffer.allocate(rep.length);
			byteBuffer.put(rep);
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
		}
	}
}