package netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	
	public void connet(String host, int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(bossGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline().addLast(new TimeClientHandler() );
					}
				});
			
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
				
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		new TimeClient().connet("127.0.0.1", 8899);
	}

}
