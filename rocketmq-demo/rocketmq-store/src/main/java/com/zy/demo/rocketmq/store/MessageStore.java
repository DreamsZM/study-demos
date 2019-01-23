package com.zy.demo.rocketmq.store;

import com.zy.demo.rocketmq.common.message.MessageExtBatch;

/**
 * This class defines constracting interfaces to implement, allowing third-party vendor to use coustomized message store
 */
public interface MessageStore {

    /**
     * load previous stored message
     * @return
     */
    boolean load();

    /**
     * lauch this message store
     */
    void start();

    /**
     * shutdown this message store
     */
    void shutdowm();

    /**
     * destroy this messae store. Generally, all persistent files should be removed after invocation
     */
    void destroy();

    /**
     * store a message into store
     * @param messageExtBrokerInner
     * @return
     */
    PutMessageResult putMessage(final MessageExtBrokerInner messageExtBrokerInner);

    /**
     * store a batch of message
     * @param messageExtBatch
     * @return
     */
    PutMessageResult putMessage(final MessageExtBatch messageExtBatch);

    /**
     *
     * @param group
     * @param topic
     * @param queueId
     * @param offset
     * @param maxMsgNums
     * @param messageFilter
     * @return
     */
    GetMessageResult getMessage(final String group, final String topic, final int queueId,
                                final long offset, final int maxMsgNums, final MessageFilter messageFilter);

    /**
     * get maximum offset of the topic queue
     * @param topic
     * @param queueId
     * @return
     */
    long getMaxOffsetInQueue(final String topic, final int queueId);

    /**
     * get the minimum offset of the topic queue
     * @param topic
     * @param queueId
     * @return
     */
    long getMinOffsetInQueue(final String topic, final int queueId);

    long getCommitLogOffsetInQueue(final String topic, final int queueId, final long consumerQueueOffset);

}
