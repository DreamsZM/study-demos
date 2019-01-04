package com.zy.demo.netty.server.netty;

import com.zy.demo.common.utils.DateUtil;
import com.zy.demo.common.utils.ThreadPoolUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    private static final String HOST = "localhost";

    private static final int PORT = 8000;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
                channelPipeline.addLast(new StringEncoder());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(HOST, PORT));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("connect success");
                } else {
                    //todo：失败重试机制，重试n次后，抛出异常
                    log.error("connect error, " + HOST + ":" + PORT);
                    throw new RuntimeException("can not connect");
                }
            }
        });

        Channel channel = channelFuture.channel();
        while (true){
            channel.writeAndFlush(DateUtil.getFormatDate() + "Hello world from client");
            log.info("send message success");
            Thread.sleep(1000);
        }
        //todo:why can not execute!!!
//        ExecutorService scheduledThreadPool = ThreadPoolUtil.getScheduledThreadPool(1);
//        ((ScheduledExecutorService) scheduledThreadPool).scheduleAtFixedRate(()->{
//            channel.writeAndFlush(DateUtil.getFormatDate() + ": Hello world from client");
//        }, 1, 1, TimeUnit.SECONDS);
//
//        scheduledThreadPool.shutdown();

    }
}
