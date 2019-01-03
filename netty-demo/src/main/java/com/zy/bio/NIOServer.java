package com.zy.bio;

import common.ThreadPoolUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOServer {

    private static final int PORT = 8888;

    public static void main(String[] args) throws IOException {
        //轮询是否有新的连接
        Selector serverSelector = Selector.open();
        //轮询是否有数据可读
        Selector clientSelector = Selector.open();
        ExecutorService singleThreadPool = ThreadPoolUtil.getThreadPool(1, 1, 1024);
        singleThreadPool.execute(()->{
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(PORT));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                //TODO:
                while (true){
                    while (serverSelector.select(1) > 0){

                        Set<SelectionKey> selectionKeySet = serverSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                        while (iterator.hasNext()){
                            SelectionKey selectionKey = iterator.next();
                            if (selectionKey.isAcceptable()){
                                try {
                                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                    socketChannel.configureBlocking(false);
                                    socketChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(()->{
            while (true){
                try {
                    while (clientSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeySet = clientSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                        while (iterator.hasNext()){
                            SelectionKey selectionKey = iterator.next();
                            if (selectionKey.isReadable()){
                                try {
                                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    socketChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
                                } finally {
                                    iterator.remove();
                                    selectionKey.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        singleThreadPool.shutdown();
        executorService.shutdown();

    }
}
