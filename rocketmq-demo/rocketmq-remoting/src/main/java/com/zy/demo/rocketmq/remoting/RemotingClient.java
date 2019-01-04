package com.zy.demo.rocketmq.remoting;


import com.zy.demo.rocketmq.remoting.protocol.RemotingCommand;

public interface RemotingClient extends RemotingService {

    RemotingCommand invokeSync(final String addr, final RemotingCommand request, final long timeoutMillis);

    void invokeAsync(final String addr, final RemotingCommand request, final long timeoutMillis, final InvokeCallback invokeCallback);

    void invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis);

}
