package com.zy.demo.rocketmq.remoting.exception;

public class RemotingException extends Exception {
    private static final long serialVersionUID = 1L;

    public RemotingException(String message){
        super(message);
    }

    public RemotingException(String message, Throwable cause){
        super(message, cause);
    }

}
