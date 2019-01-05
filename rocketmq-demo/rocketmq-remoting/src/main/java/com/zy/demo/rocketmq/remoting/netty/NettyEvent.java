package com.zy.demo.rocketmq.remoting.netty;

import io.netty.channel.Channel;
import lombok.Getter;

@Getter
public class NettyEvent {
    private final NettyEventType eventType;
    private final String remoteAddr;
    private final Channel channel;

    public NettyEvent(NettyEventType eventType, String remoteAddr, Channel channel) {
        this.eventType = eventType;
        this.remoteAddr = remoteAddr;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "NettyEvent [type=" + eventType + ", remoteAddr=" + remoteAddr + ", channel=" + channel + "]";
    }
}
