package com.zy.demo.rocketmq.remoting.netty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NettyServerConfig {
    private int listenPort = 8888;
    private int serverWorkerThreads = 8;
    private int serverCallbackExecutorThreads = 0;
    private int serverSelectorThreads = 3;
    private int serverOnewaySemaphoreValue = 256;
    private int serverSyncSemaphoreValue = 64;
    private int serverChannelMaxIdleTimeSeconds = 120;

    private int serverSocketSndBufSize = NettySystemConfig.socketSndbufSize;
    private int serverSocketRcvBufSize = NettySystemConfig.socketRcvbufSize;
    private boolean serverPooledByteBufAllocatorEnable = true;

    private boolean useEpollNativeSelector = false;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
