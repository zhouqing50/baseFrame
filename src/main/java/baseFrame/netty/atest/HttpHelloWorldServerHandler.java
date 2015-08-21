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
package baseFrame.netty.atest;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.ServerCookieDecoder;
import io.netty.handler.codec.http.ServerCookieEncoder;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class HttpHelloWorldServerHandler extends ChannelHandlerAdapter {
    
    private final StringBuilder responseContent = new StringBuilder();
    private FullHttpResponse response =  null;


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;

            if (HttpHeaderUtil.is100ContinueExpected(request)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            boolean keepAlive = HttpHeaderUtil.isKeepAlive(request);
            
            // Encode the cookie.
            String cookieString = request.headers().getAndConvert(HttpHeaderNames.COOKIE);
            if (cookieString != null) {
                Set<Cookie> cookies = ServerCookieDecoder.decode(cookieString);
                if (!cookies.isEmpty()) {
                    // Reset the cookies if necessary.
                    for (Cookie cookie: cookies) {
                        response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.encode(cookie));
                    }
                }
            } else {
                // Browser sent no cookie.  Add some.
              /*  response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.encode("key1", "value1"));
                response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.encode("key2", "value2"));*/
            }
            
            if (HttpHeaderUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }

            responseContent.append("VERSION: ").append(request.protocolVersion()).append("\r\n");
            responseContent.append("HOSTNAME: ").append(request.headers().get(HttpHeaderNames.HOST, "unknown")).append("\r\n");
            responseContent.append("REQUEST_URI: ").append(request.uri()).append("\r\n\r\n");

            HttpHeaders headers = request.headers();
            if (!headers.isEmpty()) {
                for (Map.Entry<CharSequence, CharSequence> h: headers) {
                    CharSequence key = h.getKey();
                    CharSequence value = h.getValue();
                    responseContent.append("HEADER: ").append(key).append(" = ").append(value).append("\r\n");
                }
                responseContent.append("\r\n");
            }

            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            if (!params.isEmpty()) {
                for (Entry<String, List<String>> p: params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                    	responseContent.append("PARAM: ").append(key).append(" = ").append(val).append("\r\n");
                    }
                }
                responseContent.append("\r\n");
            }

            
            
            response =  setResponse(response, responseContent);
           
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.write(response);
            }
        }
        
        if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			
			
			if (msg instanceof LastHttpContent) {
				
			}
		}
    }
    
    public  FullHttpResponse setResponse(FullHttpResponse response,  StringBuilder responseContent) {
    	
    	LoginNameServiceHelper ffHelper = new LoginNameServiceHelper();
		List<String> dtos =  ffHelper.findDomain();
    	
		 StringBuilder res = new StringBuilder();
		 res.append(responseContent);

         // create Pseudo Menu
		res.append("<html>");
		res.append("<head>");
		res.append("<title>Netty Test Form</title>\r\n");
		res.append("</head>\r\n");
		res.append("<body bgcolor=white><style>td{font-size: 12pt;}</style>");

		res.append("<table border=\"0\">");
		res.append("<tr>");
		res.append("<td>");
		res.append("<h1>Netty Test Form</h1>");
		res.append("Choose one FORM");
		res.append("</td>");
		res.append("</tr>");
         
         for (String ss : dtos) {
        	 res.append("<tr>");
        	 res.append("<td>");
        	 res.append(ss);
        	 res.append("</td>");
        	 res.append("</tr>");
		}
         
         res.append("</table>\r\n");
         
         response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(res.toString().getBytes()));
         response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
         response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
         return response;
	}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }
}
