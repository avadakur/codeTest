package http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * @author ChenjieLu
 * @description
 * @date 2022/6/16 10:35
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        System.out.println(fullHttpRequest);
        if(fullHttpRequest != null){
            String path = fullHttpRequest.uri();
            System.out.println("进入Handler");
            System.out.println(path);
            if ("/ok".equals(path) || path.contains("health")) {
                System.out.println("进入了ok或者health");
                HttpResponse res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                return;
            }
            if ("/test".equals(path)) {
                System.out.println("进入了test");
                HttpResponse res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                return;
            }
            System.out.println("进入了普通uri:将要返回not found");
            HttpResponse res = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
            ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
        }


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
