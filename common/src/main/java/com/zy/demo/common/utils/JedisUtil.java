package com.zy.demo.common.utils;

import redis.clients.jedis.Jedis;

public class JedisUtil {

    private static final String HOST = "192.168.1.141";
    private static Jedis jedis;

    public JedisUtil(){
        jedis = new Jedis(HOST);
    }

    public static void set(String key, String value){
        if (key.equals("")){
            throw new RuntimeException("key can not be null");
        }
        jedis.set(key, value);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.141");
        jedis.set("key", "value");
        System.out.println(jedis.get("key"));

    }
}
