package com.zy.demo.rocketmq.remoting;

import com.zy.demo.rocketmq.remoting.protocol.RemotingCommand;
import io.netty.channel.Channel;

public interface RemotingServer extends RemotingService {

    /**
     * 同步调用
     * @param channel
     * @param request
     * @param timeoutMillis
     * @return
     */
    RemotingCommand invokeSync(final Channel channel, final RemotingCommand request, final long timeoutMillis);

    /**
     * 异步调用
     * @param channel
     * @param request
     * @param timeoutMillis
     * @param invokeCallback
     */
    void invokeAsync(final Channel channel, final RemotingCommand request,
                     final long timeoutMillis, final InvokeCallback invokeCallback);

    /**
     * Oneway 调用
     * @param channel
     * @param request
     * @param timeoutMillis
     */
    void invokeOneway(final Channel channel, final RemotingCommand request, final long timeoutMillis);

}
