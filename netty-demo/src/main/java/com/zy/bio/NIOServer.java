package com.zy.bio;

import common.ThreadPoolUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;

public class NIOServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        ExecutorService singleThreadPool = ThreadPoolUtil.getThreadPool(1, 1, 1024);
        singleThreadPool.execute(()->{
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(PORT));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
