package com.zy.demo.rocketmq.common.message;

import java.io.Serializable;
import java.util.Map;

public class Message implements Serializable {

    private String topic;

    private int flag;

    private String transactionId;

    private Map<String, String> properties;

    private byte[] body;


}
