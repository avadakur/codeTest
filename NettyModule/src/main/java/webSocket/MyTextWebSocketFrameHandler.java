package webSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.time.LocalDateTime;

//

/**
 * TextWebSocketFrame 表示一个文本帧
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息" + msg.text());

        //回复消息

        Channel channel = ctx.channel();
        channel.writeAndFlush(new TextWebSocketFrame("服务器时间：" + LocalDateTime.now() + ",消息" + msg.text()));
    }


    /**
     * 当web客户端连接后，就会触发这个方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id 表示唯一的一个值
        System.out.println("handlerAdded被调用了" + ctx.channel().id().asLongText());
        // 不是唯一的
        System.out.println("handlerAdded被调用了" + ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemove is inject" + ctx.channel().id().asLongText());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("异常发生" + cause.getMessage());

        ctx.close();
    }
}
