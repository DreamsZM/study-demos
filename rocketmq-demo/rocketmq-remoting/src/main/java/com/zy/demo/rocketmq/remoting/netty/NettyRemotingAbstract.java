package com.zy.demo.rocketmq.remoting.netty;

import com.zy.demo.rocketmq.remoting.ChannelEventListener;
import com.zy.demo.rocketmq.remoting.InvokeCallback;
import com.zy.demo.rocketmq.remoting.RPCHook;
import com.zy.demo.rocketmq.remoting.common.Pair;
import com.zy.demo.rocketmq.remoting.common.ServiceThread;
import com.zy.demo.rocketmq.remoting.protocol.RemotingCommand;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.*;

public abstract class NettyRemotingAbstract {

    private static final Logger log = LoggerFactory.getLogger(NettyRemotingAbstract.class);

    protected final Semaphore semaphoreOneway;

    protected final Semaphore semaphoreAsync;

    protected final ConcurrentMap<Integer, ResponseFuture> responseTable =
            new ConcurrentHashMap<>(256);

    protected final HashMap<Integer, Pair<NettyRequestProcessor, ExecutorService>> processorTable =
            new HashMap<>(64);

    protected final NettyEventExecutor nettyEventExecutor = new NettyEventExecutor();

    protected Pair<NettyRequestProcessor, ExecutorService> defaultRequestProcessor;

    protected volatile SslContext sslContext;

    static {

    }

    public NettyRemotingAbstract(final int permitOneway, final int permitAsync) {
        this.semaphoreOneway = new Semaphore(permitOneway, true);
        this.semaphoreAsync = new Semaphore(permitAsync, true);
    }

    public abstract ChannelEventListener getChannelEventListener();

    public void putNettyEvent(final NettyEvent event){
        this.nettyEventExecutor.putNettyEvent(event);
    }

    public void processMessageReceived(ChannelHandlerContext ctx, RemotingCommand msg){
        final RemotingCommand command = msg;
        if (command != null){

        }
    }

    /**
     * 处理请求
     * @param ctx
     * @param cmd
     */
    public void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd){

    }

    public void processResponseCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd){

    }

    private void executeInvokeCallback(final ResponseFuture responseFuture){

    }

    public abstract RPCHook getRPCHook();

    public abstract ExecutorService getCallbackExecutor();

    public void scanResponseTable(){

    }

    /**
     * Server和Client的都需要，进行抽象
     * @param channel
     * @param request
     * @param timeoutMillis
     * @return
     */
    public RemotingCommand invokeSyncImpl(final Channel channel, final RemotingCommand request, final long timeoutMillis){
        return null;
    }

    public void invokeAsyncImpl(final Channel channel, final RemotingCommand request, final long timeoutMillis,
                                final InvokeCallback invokeCallback){

    }

    public void invokeOnewayImpl(final Channel channel, final RemotingCommand request, final long timeoutMillis){

    }

    private void requestFail(final int opaque){

    }

    protected void failFast(final Channel channel){

    }

    /**
     * 根据事件类型，调用时间监听器的响应方法进行处理
     * 对可能产生异常的地方使用try...catch进行处理
     */
    class NettyEventExecutor extends ServiceThread {

        /**
         * 阻塞队列，存放事件
         * todo: Disruptor？？？
         */
        private final LinkedBlockingDeque<NettyEvent> eventQueue = new LinkedBlockingDeque<>();

        private final int maxSize = 10000;


        public void putNettyEvent(final NettyEvent nettyEvent){
            if (this.eventQueue.size() <= maxSize){
                this.eventQueue.add(nettyEvent);
            } else {
                log.warn("event queue size [{}] enough, so drop this event {}", this.eventQueue.size(), nettyEvent.toString());
            }
        }

        @Override
        public String getServiceName() {
            return NettyEventExecutor.class.getSimpleName();
        }

        @Override
        public void run() {
            log.info(this.getServiceName() + " service started");
            //执行处理事件的具体逻辑
            final ChannelEventListener listener = NettyRemotingAbstract.this.getChannelEventListener();
            while (!isStopped()){
                try {
                    NettyEvent event = eventQueue.poll();
                    if (listener != null && event != null){
                        //根据事件类型执行相应的处理
                        switch (event.getEventType()){
                            case IDLE:
                                listener.onChannelIdle(event.getRemoteAddr(), event.getChannel());
                                break;
                            case CLOSE:
                                listener.onChannelClose(event.getRemoteAddr(), event.getChannel());
                                break;
                            case CONNECT:
                                listener.onChannelConnect(event.getRemoteAddr(), event.getChannel());
                                break;
                            case EXCEPTION:
                                listener.onChannelException(event.getRemoteAddr(), event.getChannel());
                                break;
                            default:
                                break;
                        }
                    }
                } catch (Exception e) {
                    log.warn(this.getServiceName() + " service has exception.", e);
                }

                log.info(this.getServiceName() + "service end");
            }
        }
    }
}
