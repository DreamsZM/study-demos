package com.zy.demo.rocketmq.remoting.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对Thread进行装饰
 */
public abstract class ServiceThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ServiceThread.class);

    private static final long JOIN_TIME = 90 * 1000;
    protected final Thread thread;

    protected volatile boolean hasNotified = false;
    protected volatile boolean stopped = false;

    public ServiceThread() {
        this.thread = new Thread(this, this.getServiceName());
    }

    public abstract String getServiceName();

    public void start(){
        this.thread.start();
    }

    public void shutdown(){
        shutdown(false);
    }

    public void shutdown(final boolean interrupt){
        this.stopped = true;
        log.info("shutdown thread " + this.getServiceName() + " interrupt " + interrupt);
        synchronized (thread){
            if (!this.hasNotified){
                this.hasNotified = true;
                this.notify();
            }
        }

        try {
            if (interrupt){
                this.thread.interrupt();
            }

            long beginTime = System.currentTimeMillis();
            this.thread.join(this.getJoinTime());
            long eclipseTime = System.currentTimeMillis() - beginTime;
            log.info("join thread " + this.getServiceName() + " eclipse time(ms) " + eclipseTime + " "
                    + this.getJoinTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getJoinTime(){
        return JOIN_TIME;
    }

    public boolean isStopped(){
        return stopped;
    }
}
