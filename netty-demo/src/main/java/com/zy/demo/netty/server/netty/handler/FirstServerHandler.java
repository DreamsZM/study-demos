package com.zy.demo.netty.server.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = getByteBuf(ctx);
        message.writeBytes(("client register").getBytes());
        ctx.channel().writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String clientMessage = byteBuf.toString(Charset.defaultCharset());
        System.out.println(clientMessage);

        ByteBuf message = getByteBuf(ctx);
        message.writeBytes(("Echo:" + clientMessage).getBytes());
        ctx.channel().writeAndFlush(message);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext context){
        ByteBufAllocator byteBufAllocator = context.alloc();
        ByteBuf byteBuf = byteBufAllocator.buffer();
        return byteBuf;
    }
}
