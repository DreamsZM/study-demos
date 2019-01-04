package com.zy.demo.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);


    }

    private static void print(String action, ByteBuf buffer){
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();

    }
}
