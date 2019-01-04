package com.zy.demo.netty.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) throws InterruptedException {
        //1、线程模型，提供两个线程组，一个用于处理连接请求，另一个处理连接的读写
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                //2、指定channel的模型个，NioServerSocketChannel
                .channel(NioServerSocketChannel.class)
                //3、每一条连接的处理
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new StringDecoder())
                                .addLast(new SimpleChannelInboundHandler<String>() {

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                log.info("read message from client");
                                System.out.println("From client:" + s);
                            }
                        });
                    }
                });
        bind(serverBootstrap, PORT);


    }

    /**
     * 端口绑定失败后，端口号递增后，重新绑定
     * 绑定成功后投递消息，或者进行存储到数据库、redis等存储组件，供客户端连接时使用
     * TODO：学会使用消息系统和存储系统进行系统的消息传递
     * @param serverBootstrap
     * @param port
     */
    private static void bind(ServerBootstrap serverBootstrap, int port){
        ChannelFuture channelFuture = serverBootstrap.bind(port);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    log.info("port bind success, port: " + port);
                } else {
                    log.error("port bind error, port:" + port);
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
