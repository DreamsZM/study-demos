package com.zy.demo.rocketmq.remoting;

public interface RemotingService {
    void start();

    void shutdown();

    void registerRPCHook(RPCHook rpcHook);
}
