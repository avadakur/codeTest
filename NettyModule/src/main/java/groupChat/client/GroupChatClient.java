package groupChat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {

    private final String host;

    private final int prot;



    public GroupChatClient(String host, int prot) {
        this.host = host;
        this.prot = prot;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("decoder", new StringDecoder());
                        pipeline.addLast("encoder", new StringEncoder());
                        pipeline.addLast(new ClientHandler());
                    }
                });

        ChannelFuture sync = bootstrap.connect(host, prot).sync();
        Channel channel = sync.channel();
        System.out.println("----------" + channel.localAddress() + "--------");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            channel.writeAndFlush(s);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1", 7000).run();
    }
}
