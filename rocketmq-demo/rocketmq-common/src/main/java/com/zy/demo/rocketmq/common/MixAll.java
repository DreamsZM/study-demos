package com.zy.demo.rocketmq.common;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

/**
 * TODO：
 * 设计原则：
 * 异常处理中不要包含太多的安全代码，对不能确保安全的代码进行异常处理
 */
public class MixAll {

    public static String file2String(File file) throws IOException {
        if (file.exists()){
            InputStream inputStream = null;
            byte[] data = new byte[(int) file.length()];
            boolean result;
            try {
                inputStream = new FileInputStream(file);
                //TODO:如果文件太长的话，会不会发生异常。会，但是这里够用
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

    /**
     *
     * @param fileName 文件名
     * @return
     * @throws IOException
     */
    public static String file2String(String fileName) throws IOException {
        File file = new File(fileName);
        return file2String(file);
    }

    public static void string2File(final String str, final String fileName) throws IOException {
        //新建临时文件
        String tmpFile = fileName + ".tmp";
        string2FileNotSafe(str, tmpFile);

        //备份文件
        String bakFile = fileName + ".bak";
        String prevContent = file2String(bakFile);
        if (prevContent != null){
            string2FileNotSafe(prevContent, bakFile);
        }


        //删除原文件
        File file = new File(fileName);
        file.delete();

        //将临时文件重命名
        file = new File(tmpFile);
        file.renameTo(new File(fileName));

    }

    /**
     * TODO：
     * note：
     * 操作文件时，一定要注意是否存在父目录，不存在的话，需要创建
     * @param str
     * @param fileName
     * @throws IOException
     */
    public static void string2FileNotSafe(final String str, final String fileName) throws IOException {
        File file = new File(fileName);
        //获取文件的父目录
        File fileParent = file.getParentFile();
        if (fileParent != null){
            fileParent.mkdirs();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(str);
        } catch (IOException e) {
            //TODO:
            throw e;
        } finally {
            if (fileWriter != null){
                fileWriter.close();
            }
        }

    }

    public static String properties2String(final Properties properties){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : properties.entrySet()){
            if (entry.getValue() != null){
                stringBuilder.append(entry.getKey().toString() + "=" + entry.getValue().toString() + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public static Properties string2Properties(final String str){
        if (str == null){
            return null;
        }
        Properties properties = new Properties();
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(Charset.forName("UTF-8")));
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "E:\\迅雷下载\\test\\test1\\test2\\test.txt";
        File file = new File(fileName);
        File fileParent = file.getParentFile();
        if (fileParent != null){
            boolean result = fileParent.mkdirs();
            System.out.println(String.valueOf(result));
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("test String ");
        System.out.println(file.length());
    }
}
