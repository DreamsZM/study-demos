package com.zy.demo.rocketmq.remoting.protocol;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class RemotingCommand {
    private static final String STRING_CANONICAL_NAME = String.class.getCanonicalName();
    private static final String DOUBLE_CANONICAL_NAME_1 = Double.class.getCanonicalName();
    private static final String DOUBLE_CANONICAL_NAME_2 = double.class.getCanonicalName();
    private static final String INTEGER_CANONICAL_NAME_1 = Integer.class.getCanonicalName();
    private static final String INTEGER_CANONICAL_NAME_2 = int.class.getCanonicalName();
    private static final String LONG_CANONICAL_NAME_1 = Long.class.getCanonicalName();
    private static final String LONG_CANONICAL_NAME_2 = long.class.getCanonicalName();
    private static final String BOOLEAN_CANONICAL_NAME_1 = Boolean.class.getCanonicalName();
    private static final String BOOLEAN_CANONICAL_NAME_2 = boolean.class.getCanonicalName();

    private static SerializeType serializeTypeConfigInTheisServer = SerializeType.JSON;

    //todo
    static {
        final String protocol = System.getProperty("", System.getenv(""));
        if (StringUtils.isNoneBlank(protocol)){
            try {
                serializeTypeConfigInTheisServer = SerializeType.valueOf(protocol);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("parser specified protocol error, protocol=" + protocol, e);
            }
        }
    }

    private AtomicInteger requestId = new AtomicInteger(0);
    private int opaque = requestId.getAndIncrement();
    private int flag = 0;

    public static final String SERIALIZE_TYPE_PROPERTY = "rocketmq.serialize.type";

}
