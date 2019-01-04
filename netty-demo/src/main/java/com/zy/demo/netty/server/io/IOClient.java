package com.zy.demo.netty.server.io;

import com.zy.demo.common.utils.ThreadPoolUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8000);

        try {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ScheduledExecutorService executorService = ThreadPoolUtil.getScheduledThreadPool(1);

            executorService.scheduleAtFixedRate(()->{
                try {
                    outputStream.write("Hello".getBytes());
                    byte[] bytes = new byte[1024];
                    //TODO:会阻塞？？？

//                    inputStream.read(bytes);
                    System.out.println(new String(bytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 1, 1, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
