package com.zy.demo.rocketmq.store;

/**
 * used when trying to put message
 */
public interface PutMessageLock {
    void lock();

    void unlock();
}
