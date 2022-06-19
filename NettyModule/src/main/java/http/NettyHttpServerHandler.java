package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass().getName());
        if (msg instanceof HttpRequest){
            DefaultHttpRequest httpRequest = (DefaultHttpRequest) msg;
            String uri = httpRequest.uri(); // 浏览器请求路径
            System.out.println(" 浏览器请求路径 " + uri);

            ByteBuf result = Unpooled.copiedBuffer("我是netty服务器，刚才的请求是 ：" + uri, CharsetUtil.UTF_8);
            // 返回信息给浏览器 HttpVersion version, HttpResponseStatus status, ByteBuf content
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, result);
            // 设置必要的头信息
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=utf-8");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, result.readableBytes());
            ctx.writeAndFlush(httpResponse);
        }
    }
}
