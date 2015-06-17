package netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {

	public static void main(String[] args) throws IOException {

		ServerSocket server = null;
		int port = 8899;
		try {
			server = new ServerSocket(port);
			System.out.println("time server is start .... prot="+port);
			Socket socket = null;
			while (true) {
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
			
		} catch (Exception e) {
			
		}finally{
			if (null != server) {
				System.out.println("time server is close .... prot="+port);
				server.close();
				server = null;
			}
		}
	}

}
