/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package netty.rocketmqNetty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import netty.echo.objectEcho.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler implementation for the object echo client.  It initiates the
 * ping-pong traffic between the object echo client and server by sending the
 * first message to the server.
 */
public class ObjectEchoClientHandler extends ChannelHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(ObjectEchoClientHandler.class);
    private final List<User> firstMessage;

    /**
     * Creates a client-side handler.
     */
    public ObjectEchoClientHandler() {
        firstMessage = new ArrayList<User>(ObjectEchoClient.SIZE);
        for (int i = 0; i < ObjectEchoClient.SIZE; i++) {
            firstMessage.add(new User(i, "test" + i));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the first message if this handler is a client-side handler.

        RemotingCommand request = RemotingCommand.createRequestCommand(0);
        request.setRemark(String.valueOf(1));
        long timeoutMillis = 1000 * 3;
        InvokeCallback invokeCallback = responseFuture -> System.out.println(responseFuture.getResponseCommand() + "Ok=" + responseFuture.isSendRequestOK());

        final ResponseFuture responseFuture = new ResponseFuture(request.getOpaque(), timeoutMillis, invokeCallback);
        ctx.writeAndFlush(request).addListener(f -> {
            if (f.isSuccess()) {
                responseFuture.setSendRequestOK(true);
                System.out.println("send msg success");
                return;
            } else {
                System.out.println("send msg failed");
                responseFuture.setSendRequestOK(false);
            }

            responseFuture.putResponse(null);
            try {
                responseFuture.executeInvokeCallback();
            } catch (Throwable e) {
                logger.warn("excute callback in writeAndFlush addListener, and callback throw", e);
            } finally {
                responseFuture.release();
            }

            logger.warn("send a request command to channel <{}> failed.");
            logger.warn(request.toString());
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Echo back the received object to the server.
        System.out.println(msg);
        //ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
