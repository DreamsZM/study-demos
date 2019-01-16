package com.zy.demo.rocketmq.store;

import com.zy.demo.rocketmq.store.config.MessageStoreConfig;

import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TransientStorePool {

    private final int poolSize;

    private final int fileSize;

    private final MessageStoreConfig storeConfig;

    private final Deque<ByteBuffer> availableBuffers;

    public TransientStorePool(MessageStoreConfig storeConfig) {
        this.storeConfig = storeConfig;
        this.poolSize = 0;
        this.fileSize = 0;
        this.availableBuffers = new ConcurrentLinkedDeque<>();
    }
}
