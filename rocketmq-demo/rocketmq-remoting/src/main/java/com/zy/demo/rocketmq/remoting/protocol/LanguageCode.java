package com.zy.demo.rocketmq.remoting.protocol;

import lombok.Getter;

/**
 * 使用的语言类型
 */
@Getter
public enum LanguageCode {
    JAVA((byte)0);

    private byte code;

    LanguageCode(byte code) {
        this.code = code;
    }

    public static LanguageCode valueOf(byte code){
        for (LanguageCode languageCode : LanguageCode.values()){
            if (languageCode.getCode() == code){
                return languageCode;
            }
        }
        return null;
    }
}
