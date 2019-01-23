package com.zy.demo.rocketmq.srvutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWatchService {
    private static final Logger log = LoggerFactory.getLogger(FileWatchService.class);


    private String hash(String filePath){

        return null;
    }

    public interface Listener{
        /**
         * Will be called when the target files are changed
         * @param path
         */
        void onChanged(String path);
    }
}
