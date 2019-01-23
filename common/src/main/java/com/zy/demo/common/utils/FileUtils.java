package com.zy.demo.common.utils;

import java.io.*;

public class FileUtils {

    public static String file2String(final File file) throws IOException {
        if (file.exists()){
            InputStream inputStream = null;
            byte[] data = new byte[(int) file.length()];
            boolean result ;
            try {
                inputStream = new FileInputStream(file);
                int len = inputStream.read(data);
                result = len == data.length;
            } finally {
                if (inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (result){
                return new String(data);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        if (null instanceof Object){
            System.out.println("test");
        } else {
            System.out.println("test2");
        }
    }
}
