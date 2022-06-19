package codec;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class NettyClientChannel extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new NettyClientHandler());
    }
}
