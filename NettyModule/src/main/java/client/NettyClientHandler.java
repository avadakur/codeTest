package client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当通道就绪时， 就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client :" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("你妈的 好难", CharsetUtil.UTF_8));

    }

    // 通道有读取事件时，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
        logger.info("server 回复的消息：{}", buf.toString(CharsetUtil.UTF_8));
        logger.info("server id:{}", ctx.channel().remoteAddress());
//        System.out.println("server 回复的消息：" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("server 的ip地址" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.toString());

        ctx.channel().close();
    }
}
