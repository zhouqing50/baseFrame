/*
 * Copyright 2013 The Netty Project
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
package netty.echo.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class HttpHelloWorldServerHandler extends ChannelHandlerAdapter {

    private ByteBuf buffer_body = UnpooledByteBufAllocator.DEFAULT.buffer();
    /*
     * for debug
     */
    private StringBuffer sb_debug = new StringBuffer();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            sb_debug.append("\n>> HTTP REQUEST -----------\n");
            sb_debug.append(request.getProtocolVersion().toString())
                    .append(" ").append(request.getMethod().name())
                    .append(" ").append(request.getUri());
            sb_debug.append("\n");
            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers) {
                    sb_debug.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
                }
            }
            sb_debug.append("\n");
            System.out.println(sb_debug);
        } else if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf thisContent = content.content();
            if (thisContent.isReadable()) {
                buffer_body.writeBytes(thisContent);
            }
            if (msg instanceof LastHttpContent) {
                sb_debug.append(buffer_body.toString(CharsetUtil.UTF_8));
                LastHttpContent trailer = (LastHttpContent) msg;
                if (!trailer.trailingHeaders().isEmpty()) {
                    for (CharSequence name : trailer.trailingHeaders().names()) {
                        sb_debug.append(name).append("=");
                        for (CharSequence value : trailer.trailingHeaders().getAll(name)) {
                            sb_debug.append(value).append(",");
                        }
                        sb_debug.append("\n\n");
                    }
                }
                sb_debug.append("\n<< HTTP REQUEST -----------");
            }
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(sb_debug.toString().getBytes()));
        response.headers().set(CONTENT_TYPE, "text/plain");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
