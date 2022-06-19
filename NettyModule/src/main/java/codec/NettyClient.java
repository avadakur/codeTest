package codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {


        //客户端需要一个事件循环组

        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        //客户端使用的不是ServerBootstrap 而是Bootstrap
        Bootstrap bootstrap = new Bootstrap();


        try {
            bootstrap.group(eventExecutors)//设置线程组
                    .channel(NioSocketChannel.class)// 设置客户端通道的实现类
                    .handler(new NettyClientChannel());

            System.out.println("客户端 ok");

            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6668).sync();

            sync.channel().closeFuture().sync();
        } catch (Exception e) {

        }finally {

        }


    }
}
