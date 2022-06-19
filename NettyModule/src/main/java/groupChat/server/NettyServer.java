package groupChat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {


    public static void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new NettyChannelInitializer());
            System.out.println("服务器 is ready");

            //绑定一个端口并且同步，生成一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture sync = bootstrap.bind(port).sync();

            //对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            bossGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        NettyServer.run(7000);
    }
}
