package netty.nio;

import java.io.IOException;

public class TimeServer {

	public static void main(String[] args) throws IOException {
		new Thread(new MultiplexerTimeServer(8899)).start();
	}

}
