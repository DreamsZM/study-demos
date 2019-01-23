package com.zy.demo.rocketmq.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadFactoryImpl implements ThreadFactory {
    private final AtomicLong atomicLong = new AtomicLong(0);
    private final String threadNamePrefix;
    private final boolean daemon;

    public ThreadFactoryImpl(final String threadNamePrefix){
        this(threadNamePrefix, false);
    }

    public ThreadFactoryImpl(final String threadNamePrefix, final boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, threadNamePrefix + atomicLong.getAndIncrement());
        thread.setDaemon(daemon);
        return thread;
    }
}
