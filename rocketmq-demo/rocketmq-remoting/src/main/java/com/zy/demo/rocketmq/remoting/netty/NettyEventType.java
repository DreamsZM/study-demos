package com.zy.demo.rocketmq.remoting.netty;

public enum NettyEventType {
    /**
     * 连接
     */
    CONNECT,

    /**
     * close
     */
    CLOSE,

    /**
     * idle
     */
    IDLE,

    /**
     * exception
     */
    EXCEPTION
}
