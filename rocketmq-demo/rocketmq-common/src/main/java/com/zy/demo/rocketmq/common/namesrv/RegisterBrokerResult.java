package com.zy.demo.rocketmq.common.namesrv;

import com.zy.demo.rocketmq.common.protocol.body.KVTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 注册结果
 */
@Getter
@Setter
public class RegisterBrokerResult {
    /**
     * TODO:
     */
    private String haServerAddr;

    /**
     * master address
     */
    private String masterAddr;

    /**
     * TODO:
     */
    private KVTable kvTable;
}
