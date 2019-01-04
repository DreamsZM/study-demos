package com.zy.demo.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufUtil {
    private static ByteBuf byteBuf;

    public ByteBufUtil(){
        byteBuf = Unpooled.buffer();
    }

    public static ByteBuf getByteBuf(String msg){
        return byteBuf.writeBytes(msg.getBytes());
    }
}
