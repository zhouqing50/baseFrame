package netty.echo.decoder;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter{

	 private byte[] req ;
	 private int count;
	 
	 public TimeClientHandler() throws UnsupportedEncodingException {
		 req =("get time order"+System.getProperty("line.separator")).getBytes("utf-8");
	 }

	 @Override
	    public void channelActive(ChannelHandlerContext ctx) {
			 ByteBuf message = null; 
			 for (int i = 0; i <100; i++) {
				 message = Unpooled.buffer(req.length);
				 message.writeBytes(req);
				 ctx.writeAndFlush(message);
			}
	    }

	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
	    	
	    	String body = (String) msg;
	    	System.out.println("now is "+body+"count is "+ ++count);
	        //ctx.write(msg);
	    }

	    @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) {
	       ctx.flush();
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        // Close the connection when an exception is raised.
	        cause.printStackTrace();
	        ctx.close();
	    }
}
