package com.zy.demo.rocketmq.remoting;

import com.zy.demo.rocketmq.remoting.protocol.RemotingCommand;

/**
 *
 */
public interface RPCHook {

    /**
     *
     * @param remoteAddr
     * @param request
     */
    void doBeforeRequest(final String remoteAddr, final RemotingCommand request);

    /**
     *
     * @param remoteAddr
     * @param request
     * @param reponse
     */
    void doAfterReponse(final String remoteAddr, final RemotingCommand request, final RemotingCommand reponse);
}
