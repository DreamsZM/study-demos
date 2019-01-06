package com.zy.demo.rocketmq.remoting.protocol;

import com.zy.demo.rocketmq.remoting.CommandCustomHeader;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class RemotingCommand {
    public static final String SERIALIZE_TYPE_PROPERTY = "rocketmq.serialize.type";
    public static final String SERIALIZE_TYPE_ENV = "ROCKETMQ_SERIALIZE_TYPE";
    public static final String REMOTING_VERSION_KEY = "rocketmq.remoting.version";

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
        //从系统中获取属性值，
        final String protocol = System.getProperty(SERIALIZE_TYPE_PROPERTY, System.getenv(SERIALIZE_TYPE_ENV));
        if (StringUtils.isNoneBlank(protocol)){
            try {
                serializeTypeConfigInTheisServer = SerializeType.valueOf(protocol);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("parser specified protocol error, protocol=" + protocol, e);
            }
        }
    }

    private int code;
    private LanguageCode languageCode = LanguageCode.JAVA;
    private int version = 0;

    private static volatile int configVersion = -1;
    private AtomicInteger requestId = new AtomicInteger(0);
    private int opaque = requestId.getAndIncrement();
    private int flag = 0;
    private String remark;
    private HashMap<String, String> extFields;

    protected RemotingCommand() {}

    public static RemotingCommand createRequestCommand(int code, CommandCustomHeader customHeader){
        RemotingCommand cmd = new RemotingCommand();

        return cmd;
    }

    /**
     * todo:
     * Class<? extends CommandCustomHeader> classHeader 和 CommandCustomHeader 的区别是什么
     * @param classHeader
     * @return
     */
    public static RemotingCommand createResponseCommand(Class<? extends CommandCustomHeader> classHeader){
        return createResponseCommand(RemotingSysReponseCode.SYSTEM_ERROR, "not set any response code", classHeader);
    }

    public static RemotingCommand createResponseCommand(int code, String remark, Class<? extends CommandCustomHeader> classHeader){
        return null;
    }

    public static RemotingCommand createResponseCommand(int code, String remark){
        return createResponseCommand(code, remark, null);
    }

    private static void setCmdVersion(RemotingCommand cmd){
        if (configVersion >= 0){
            cmd.setVersion(configVersion);
        } else {
            String v = System.getProperty(REMOTING_VERSION_KEY);
            if (v != null){
                int value = Integer.parseInt(v);
                configVersion = value;
                cmd.setVersion(value);
            }
        }
    }



}
