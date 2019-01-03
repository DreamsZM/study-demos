package com.zy.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8888);

        try {
            socket.connect(new InetSocketAddress(8888));
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
