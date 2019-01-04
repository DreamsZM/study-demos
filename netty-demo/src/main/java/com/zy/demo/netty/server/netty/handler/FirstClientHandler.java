package com.zy.demo.netty.server.netty.handler;

import com.zy.demo.common.utils.ByteBufUtil;
import com.zy.demo.common.utils.DateUtil;
import com.zy.demo.common.utils.ThreadPoolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接建立成功调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        System.out.println("客户端写出数据");
        ByteBuf byteBuf = getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.toString(Charset.defaultCharset()));
    }



    private ByteBuf getByteBuf(ChannelHandlerContext context){
        ByteBufAllocator allocator = context.alloc();
        ByteBuf byteBuf = allocator.buffer();
        byteBuf.writeBytes("Hello, this is a message from client".getBytes(Charset.defaultCharset()));
        return byteBuf;
    }
}
