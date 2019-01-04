package com.zy.demo.common.utils;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    public static ExecutorService getThreadPool(int corePoolSize,
                                                int maximumPoolSize,
                                                long keepAliveTime,
                                                TimeUnit unit,
                                                BlockingQueue<Runnable> workQueue,
                                                ThreadFactory threadFactory,
                                                RejectedExecutionHandler handler){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    public static ExecutorService getThreadPool(int corePoolSize, int maximumPoolSize, int queueCapacity){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(queueCapacity),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    public static ScheduledExecutorService getScheduledThreadPool(int corePoolSize){
        return new ScheduledThreadPoolExecutor(corePoolSize, new NamedThreadFactory());
    }

}
