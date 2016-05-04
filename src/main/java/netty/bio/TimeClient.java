package netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {
	
	public void getTime() {
		Socket socket = null;
		int port = 8899;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			socket = new Socket("127.0.0.1",port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("get time order");
			System.out.println(" time client send order...");
			String resp = in.readLine();
			System.out.println("Now is "+resp);
		} catch (Exception e) {
			
		}finally{
			if (null != in) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				out.close();
				out = null;
			}
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			TimeClient client = new TimeClient();
			client.getTime();
		}
		
	}

}
