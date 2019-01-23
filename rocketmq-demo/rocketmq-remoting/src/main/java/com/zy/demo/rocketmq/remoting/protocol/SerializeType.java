package com.zy.demo.rocketmq.remoting.protocol;

import lombok.Getter;

/**
 * @author Zhao Min
 * 提供两种序列化类型
 */
@Getter
public enum SerializeType {
    JSON((byte)0),

    ROCKETMQ((byte)1);

    private byte code;

    SerializeType(byte code) {
        this.code = code;
    }

    public static SerializeType valueOf(byte code){
        for (SerializeType serializeType : SerializeType.values()){
            if (serializeType.getCode() == code){
                return serializeType;
            }
        }
        return null;
    }

    private static boolean isBlank(String str){
        int strLen;
        if (str == null || (strLen = str.length()) == 0){
            return true;
        }

        for (int i = 0; i < strLen; i++ ){
            if (!Character.isWhitespace(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
