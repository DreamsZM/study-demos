package com.zy.demo.rocketmq.remoting.netty;

import com.zy.demo.rocketmq.remoting.ChannelEventListener;
import com.zy.demo.rocketmq.remoting.InvokeCallback;
import com.zy.demo.rocketmq.remoting.RPCHook;
import com.zy.demo.rocketmq.remoting.RemotingClient;
import com.zy.demo.rocketmq.remoting.protocol.RemotingCommand;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;

public class NettyRemotingClient extends NettyRemotingAbstract implements RemotingClient {
    private static final Logger log = LoggerFactory.getLogger(NettyRemotingClient.class);

    /**
     * TODO:
     * @param permitOneway
     * @param permitAsync
     */
    public NettyRemotingClient(int permitOneway, int permitAsync) {
        super(permitOneway, permitAsync);
    }

    @Override
    public RemotingCommand invokeSync(String addr, RemotingCommand request, long timeoutMillis) {
        return null;
    }

    @Override
    public void invokeAsync(String addr, RemotingCommand request, long timeoutMillis, InvokeCallback invokeCallback) {

    }

    @Override
    public void invokeOneway(String addr, RemotingCommand request, long timeoutMillis) {

    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void registerRPCHook(RPCHook rpcHook) {

    }

    @Override
    public ChannelEventListener getChannelEventListener() {
        return null;
    }

    @Override
    public RPCHook getRPCHook() {
        return null;
    }

    @Override
    public ExecutorService getCallbackExecutor() {
        return null;
    }

    /**
     *
     */
    static class ChannelWrapper{

    }

    class NettyClientHandler extends SimpleChannelInboundHandler<RemotingCommand>{

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemotingCommand remotingCommand) throws Exception {

        }
    }

    class NettyConnectManageHandler extends ChannelDuplexHandler{
        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            super.connect(ctx, remoteAddress, localAddress, promise);
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            super.disconnect(ctx, promise);
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            super.close(ctx, promise);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}
