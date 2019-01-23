package com.zy.demo.rocketmq.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 抽象类
 */
public abstract class ConfigManager {
    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    /**
     * 从configFilePath加载文件，如果失败，则加载备份文件
     * @return
     */
    public boolean load(){
        String fileName = null;
        try {
            fileName = this.configFilePath();
            String jsonString = MixAll.file2String(fileName);

            if (null == jsonString || jsonString.length() == 0){
                return this.loadBak();
            } else {
                this.decode(jsonString);
                log.info("load" + fileName + " OK");
                return true;
            }
        } catch (IOException e) {
            log.error("load " + fileName + " failed, and try to load backup file", e);
            return this.loadBak();
        }
    }

    public boolean loadBak(){
        String fileName = null;
        try {
            fileName = this.configFilePath();
            String jsonString = MixAll.file2String(fileName + ".bak");
            if (jsonString !=null && jsonString.length() > 0){
                this.decode(jsonString);
                log.info("load" + fileName + " OK");
                return true;
            }
        } catch (IOException e) {
            log.error("load " + fileName + " Failed", e);
            return false;
        }

        return true;
    }

    public synchronized void persist(){
        String jsonString = this.encode(true);
        if (jsonString != null){
            String fileName = this.configFilePath();
            try {
                MixAll.string2File(jsonString, fileName);
            } catch (IOException e) {
                log.error("persist file " + fileName + " exception", e);
            }
        }
    }

    public abstract String configFilePath();

    public abstract String encode();

    public abstract String encode(final boolean prettyFormat);

    public abstract void decode(final String jsonString);
}
