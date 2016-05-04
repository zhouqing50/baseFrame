package netty.echo.decoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

	public void bind(int port) {
		 // Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChannelInitializer<SocketChannel>() {
	                 @Override
	                 public void initChannel(SocketChannel ch) throws Exception {
	                	 ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
	                	 ch.pipeline().addLast(new StringDecoder());
	                     ch.pipeline().addLast(new TimeServerHandler());
	                 }
				});
			
			 // Start the server.
            ChannelFuture f = b.bind(port).sync();
            System.out.println("timeServer is start");
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		new TimeServer().bind(7799);
	}

}
