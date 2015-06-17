package netty.echo.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class TimeServerHandler extends ChannelHandlerAdapter {

	private String ORDER = "get time order";
	private int count;
	
	   @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		 
		   String body = (String) msg;
		   System.out.println("timeServer receive order: "+body+"count is :"+ ++count);
		   String currentTime = ORDER.equals(body)?new Date(System.currentTimeMillis()).toString():"bad order!";
		   currentTime = currentTime + System.getProperty("line.separator");
		   ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
	        ctx.writeAndFlush(resp);
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        // Close the connection when an exception is raised.
	        cause.printStackTrace();
	        ctx.close();
	    }
}
