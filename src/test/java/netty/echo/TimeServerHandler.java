package netty.echo;

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
		   ByteBuf buf = (ByteBuf) msg;
		   byte[] req  = new byte[buf.readableBytes()];
		   buf.readBytes(req);
		   String body = new String(req,"utf-8").substring(0, req.length-System.getProperty("line.separator").length());
		   System.out.println("timeServer receive order: "+body+"count is :"+ ++count);
		   String currentTime = ORDER.equals(body)?new Date(System.currentTimeMillis()).toString():"bad order!";
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
