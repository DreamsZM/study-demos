package com.zy.common.utils;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {

    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(new File(""));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key, String defaultValue){
        if (key == null){
            throw new IllegalArgumentException("key can't null");
        }
        return properties.getProperty(key, defaultValue);
    }

    public static String getProperty(String key){
        return getProperty(key, "");
    }

}
