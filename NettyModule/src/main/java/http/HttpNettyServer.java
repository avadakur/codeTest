package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import server.NettyChannelInitializer;

/**
 * @author ChenjieLu
 * @description
 * @date 2022/6/16 9:36
 */
public class HttpNettyServer {


    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new HttpServerInit());
            System.out.println("服务器 is ready");

            //绑定一个端口并且同步，生成一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture sync = bootstrap.bind("127.0.0.1",8081).sync();

            //对关闭通道进行监听
            sync.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {

        }
    }
}
