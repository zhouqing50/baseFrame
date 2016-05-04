package netty.protobuf.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import netty.protobuf.entity.AuthProbuf;

import java.io.UnsupportedEncodingException;

public class PbServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {

        AuthProbuf.AuthRequest request = (AuthProbuf.AuthRequest) msg;
        System.out.println("request: userId=" + request.getUserId() + ", password=" + request.getPassword());
        AuthProbuf.AuthResponse response = AuthProbuf.AuthResponse.newBuilder().setResultCode(0).setResultMessage("success").build();
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
