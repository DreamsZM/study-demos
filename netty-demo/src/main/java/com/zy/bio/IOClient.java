package com.zy.bio;

import com.zy.common.utils.ThreadPoolUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8888);

        try {
            OutputStream outputStream = socket.getOutputStream();
            ScheduledExecutorService executorService = ThreadPoolUtil.getScheduledThreadPool(1);

            executorService.scheduleAtFixedRate(()->{
                try {
                    outputStream.write("Hello".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 1, 1, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
