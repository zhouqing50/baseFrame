package netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{
	
	private Socket socket;
	private String ORDER = "get time order";
	
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			String currentTime = "";
			String body = null;
			while (true) {
				body = in.readLine();
				if (null != body) {
					System.out.println("time server receive order..."+body+"----thread---"+Thread.currentThread().getId());
					currentTime = ORDER.equals(body)?new Date(System.currentTimeMillis()).toString():"bad order!";
					out.println(currentTime);
				}
			}
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

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
