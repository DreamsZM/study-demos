package com.zy.demo.rocketmq.common.protocol.body;

import com.zy.demo.rocketmq.remoting.protocol.RemotingSerializable;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class KVTable extends RemotingSerializable {
    private HashMap<String, String> table = new HashMap<>();
}
