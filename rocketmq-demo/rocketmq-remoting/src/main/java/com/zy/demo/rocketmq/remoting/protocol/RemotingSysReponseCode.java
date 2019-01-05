package com.zy.demo.rocketmq.remoting.protocol;

/**
 * 系统响应码
 */
public interface RemotingSysReponseCode {

    int success = 0;

    int SYSTEM_ERROR = 1;

    int SYSTEM_BUSY = 2;

    int REQUEST_CODE_NOT_SUPPORTED = 3;

    int TRANSACTION_FAILED  = 4;
}
