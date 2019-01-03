package com.zy.bio;

import common.NamedThreadFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IOServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            ThreadFactory threadFactory = new NamedThreadFactory();
            ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0,
                    TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024),
                    threadFactory, new ThreadPoolExecutor.AbortPolicy());
            singleThreadPool.execute(()->{
                while (true){
                    try {
                        Socket socket = serverSocket.accept();
                        new Thread(()->{
                            try {
                                InputStream inputStream = null;
                                inputStream = socket.getInputStream();
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                byte[] bytes = new byte[1024];
                                int len;
                                while ((len=bufferedInputStream.read(bytes)) != -1){
                                    String msg = new String(bytes);
                                    System.out.println(msg);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
