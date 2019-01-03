package com.zy.bio;

import com.zy.common.utils.NamedThreadFactory;
import com.zy.common.utils.ThreadPoolUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class IOServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);

            ThreadFactory threadFactory = new NamedThreadFactory();

            ExecutorService singleThreadPool = ThreadPoolUtil.getThreadPool(1, 1, 1024);
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
