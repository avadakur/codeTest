package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class NettyChannelInitializer extends ChannelInitializer<Channel> {
    //给pipeline 设置处理器
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();


        pipeline.addLast(new NettyServerHandler());

    }
}
