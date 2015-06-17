package netty.nio;

public class TimeClient {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new TimeClientHandle("127.0.0.1", 8899)).start();
		}
		
	}

}
