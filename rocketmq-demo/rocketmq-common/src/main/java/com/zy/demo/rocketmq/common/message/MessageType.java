package com.zy.demo.rocketmq.common.message;

/**
 * Message type:
 * normal msg,
 * delay msg,
 * transaction msg half,
 * transaction msg commit
 */
public enum MessageType {
    /**
     * 标准消息
     */
    NORMAL_MSG,

    /**
     * 事务消息，TODO：
     */
    TRANS_MSG_HALF,

    /**
     * 事务提交消息
     */
    TRANS_MSG_COMMIT,

    /**
     * 延迟消息
     */
    DELAY_MSG,
}
