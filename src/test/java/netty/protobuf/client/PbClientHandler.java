package netty.protobuf.client;

import java.io.UnsupportedEncodingException;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import netty.protobuf.entity.AuthProbuf;

public class PbClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        AuthProbuf.AuthRequest request = AuthProbuf.AuthRequest.newBuilder().setUserId("010203").setPassword("abcde").build();
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {

        AuthProbuf.AuthResponse response=(AuthProbuf.AuthResponse)msg;
        System.out.println("response: code="+response.getResultCode()+", message="+response.getResultMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
