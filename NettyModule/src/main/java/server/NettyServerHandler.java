package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;


// 自定义一个handler 需要继承netty 规定好的handlerAdapter
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    //读取事件

    /**
     * 1.ChannelHandlerContext ctx 上下文对象 含有管道pipeline 通道
     * 2. Object msg 客户端发来的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

        //放到任务队列里taskQueue
        EventLoop eventExecutors = ctx.channel().eventLoop();
        eventExecutors.execute(()->{
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("taskQueue任务执行完毕",CharsetUtil.UTF_8));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        // 定时任务 两个都是在5秒后执行
        eventExecutors.schedule(() -> {
            logger.info("定时任务执行开始");
        }, 5, TimeUnit.SECONDS);
        eventExecutors.schedule(() -> {
            logger.info("定时任务执行开始2");
        }, 5, TimeUnit.SECONDS);
        System.out.println("server ctx :{}" + ctx);
        System.out.println("开始读取数据");
        System.out.println(msg.toString());
        //将msg转换成ByteBuf
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println("转换后的数据:"+ buffer.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());

    }


    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //把数据写到缓存区，并且刷新
        //对发送的数据编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端", CharsetUtil.UTF_8));
    }

    //发生异常

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.channel().close();
    }
}
