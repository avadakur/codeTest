package http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author ChenjieLu
 * @description
 * @date 2022/6/16 10:34
 */
public class HttpServerInit extends ChannelInitializer<Channel> {
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
        pipeline.addLast(new HttpServerHandler());
    }
}
