package com.zy.demo.netty.server.netty;

import com.zy.demo.common.utils.DateUtil;
import com.zy.demo.common.utils.ThreadPoolUtil;
import com.zy.demo.netty.server.netty.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    private static final String HOST = "localhost";

    private static final int PORT = 8001;
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                //绑定自定义属性，通过channel.attr()获取
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                //连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //是够开启TCP底层心跳机制
                .option(ChannelOption.SO_KEEPALIVE, true)
                //Nagle算法，开启减少发送次数，减少网络流量，但是会有延迟
                .option(ChannelOption.TCP_NODELAY, false)
                .handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                ChannelPipeline channelPipeline = nioSocketChannel.pipeline();
                channelPipeline.addLast(new FirstClientHandler());
            }
        });

        connect(bootstrap, HOST, PORT, MAX_RETRY);

//        Channel channel = channelFuture.channel();
//        while (true){
//            channel.writeAndFlush(DateUtil.getFormatDate() + "Hello world from client");
//            log.info("send message success");
//            Thread.sleep(1000);
//        }
        //todo:why can not execute!!!
//        ExecutorService scheduledThreadPool = ThreadPoolUtil.getScheduledThreadPool(1);
//        ((ScheduledExecutorService) scheduledThreadPool).scheduleAtFixedRate(()->{
//            channel.writeAndFlush(DateUtil.getFormatDate() + ": Hello world from client");
//        }, 1, 1, TimeUnit.SECONDS);
//
//        scheduledThreadPool.shutdown();

    }

    /**
     * 利用递归进行重试连接
     * todo:指定重试次数，防止一直重试，无法连接成功
     * @param bootstrap
     * @param host
     * @param port
     */
    private static ChannelFuture connect(Bootstrap bootstrap, String host, int port){
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()){
                    log.info("connect sucess, host:" + host + ", port:" + port);
                } else {
                    log.error("connect error, 重试");
                    connect(bootstrap, host, port);
                }
            }
        });

        return channelFuture;
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        ChannelFuture channelFuture = bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()){
                log.info("connect sucess, host:" + host + ", port:" + port);
            } else if (retry == 0){
                log.error("重试结束，无法建立连接");
                throw new RuntimeException("连接失败");
            } else {
                int order = MAX_RETRY - retry + 1;
                //根据重试次数，设置不同的延迟时间
                int delay = 1 << order;
                //获取启动时设置的线程组
                log.error("重试" + order + "次");
                //获取线程组执行定时任务
                NioEventLoopGroup eventLoopGroup = (NioEventLoopGroup) bootstrap.config().group();
                eventLoopGroup.schedule(()->{
                    connect(bootstrap, host, port, retry - 1);
                }, delay, TimeUnit.SECONDS);
            }
        });
    }

}
