package groupChat.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


// 自定义一个handler 需要继承netty 规定好的handlerAdapter
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    public static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(Unpooled.copiedBuffer(LocalDateTime.now() + "[客戶端]" + channel.remoteAddress() + "上线了！！！", CharsetUtil.UTF_8));

        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush(Unpooled.copiedBuffer("[客户端]" + channel.remoteAddress() + "离开了", CharsetUtil.UTF_8));
        System.out.println("channelGroup Size 减少");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线了");
    }

    //读取事件
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String str) throws Exception {

        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(ch->{
            if (channel != ch) {
                //转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送消息" + str);
            } else {
                ch.writeAndFlush("[自己]发了送消息" + str);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
