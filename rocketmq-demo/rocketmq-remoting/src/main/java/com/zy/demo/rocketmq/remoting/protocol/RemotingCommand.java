package com.zy.demo.rocketmq.remoting.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import com.zy.demo.rocketmq.remoting.CommandCustomHeader;
import com.zy.demo.rocketmq.remoting.annotation.CFNotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
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

    public static final int RPC_TYPE = 0;
    public static final int RPC_ONEWAY = 1;
    /**
     * mark oneway, type
     */
    private int flag = 0;
    private String remark;
    private HashMap<String, String> extFields;

    private transient CommandCustomHeader customHeader;
    private transient byte[] body;

    private SerializeType serializeTypeCurrentRPC = serializeTypeConfigInTheisServer;

    /**
     * 显式声明泛型，能够增强兼容性
     */
    private static final Map<Class<? extends CommandCustomHeader>, Field[]> CLASS_HASH_MAP =
            new HashMap<Class<? extends CommandCustomHeader>, Field[]>();
    private static final Map<Class, String> CANONICAL_NAME_CASHE = new HashMap<>();
    private static final Map<Field, Boolean> NULLABLE_FIELD_CACHE = new HashMap<>();

    protected RemotingCommand() {}

    /**
     *
     * @param code TODO：
     * @param customHeader 自定义的请求头
     * @return
     */
    public static RemotingCommand createRequestCommand(int code, CommandCustomHeader customHeader){
        RemotingCommand cmd = new RemotingCommand();

        return cmd;
    }

    /**
     * Class<? extends CommandCustomHeader> classHeader 和 CommandCustomHeader 的区别是什么
     * 利用反射实例化对象，只提供类即可实现依赖注入
     * 不需要自己创建对象
     * TODO:优点？？？
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

    /**
     * command的encode 和 decode
     */

    public static int getHeaderLength(int length){
        //todo:

    }

    private static RemotingCommand headerDecode(byte[] headerData, SerializeType type){
        //根据序列化类型进行序列化
    }


    /**
     *
     * @param array 字节数组，封装成ByteBuffer后decode
     * @return
     */
    public static RemotingCommand decode(final byte[] array){
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return decode(buffer);
    }
    public static RemotingCommand decode(final ByteBuffer byteBuffer){

    }

    /**
     * 对body进行encode
     * TODO: 使用netty 传输，编码为ByteBuf
     * @param bodyLength
     * @return
     */
    public ByteBuffer encodeHeader(final int bodyLength){
        int length = 4;

        byte[] headerData;
        headerData = this.headerEncode();

        length += headerData.length;

        length += bodyLength;

        ByteBuffer result = ByteBuffer.allocate(4 + length - bodyLength);

        /**
         * 整条命令的长度，包括body
         */
        result.putInt(length);

        //header length
        result.put(markProtocolType(headerData.length, serializeTypeCurrentRPC));

        result.put(headerData);

        result.flip();
        return null;
    }

    public ByteBuffer encodeHeader(){
        return encodeHeader((this.body != null) ? body.length : 0);
    }

    /**
     * 对RemotingCommand 进行编码
     * length
     * head length
     * head data
     * body data
     * @return
     */
    public ByteBuffer encode(){
        int length = 4;

    }

    private byte[] headerEncode(){

    }

    public void makeCustomHeaderToNet(){

    }

    public static byte[] markProtocolType(int source, SerializeType type){
        byte[] result = new byte[4];

        result[0] = type.getCode();
        result[1] = (byte)((source >> 16) & 0xFF);
        result[2] = (byte) ((source >> 8) & 0xFF);
        result[3] = (byte) (source & 0xFF);
        return result;
    }

    /**
     * 使用位运算标记
     */
    public void markOneWayRPC(){
        int bits = 1 << RPC_ONEWAY;
        this.flag |= bits;
    }

    @JSONField(serialize = false)
    public boolean isOneway(){
        int bits = 1 << RPC_ONEWAY;
        return (this.flag & bits) == bits;
    }

    @JSONField(serialize = false)
    public RemotingCommandType getType(){
        if (isResponseType()){
            return RemotingCommandType.REPONSE_COMMAND;
        }

        return RemotingCommandType.REQUEST_COMMAND;
    }

    /**
     * flag 的二进制形式的最后一位是1时为响应类型
     * @return
     */
    @JSONField(serialize = false)
    public boolean isResponseType(){
        int bits = 1 << RPC_TYPE;
        return (this.flag & bits) == bits;
    }

    public Field[] getClazzFields(Class<? extends CommandCustomHeader> classHeader){
        Field[] fields = CLASS_HASH_MAP.get(classHeader);
        if (fields == null){
            fields = classHeader.getDeclaredFields();
            synchronized (CLASS_HASH_MAP){
                CLASS_HASH_MAP.put(classHeader, fields);
            }
        }
        return fields;
    }

    public boolean isFieldNullable(Field field){
        if (!NULLABLE_FIELD_CACHE.containsKey(field)){
            Annotation annotation = field.getAnnotation(CFNotNull.class);
            synchronized (NULLABLE_FIELD_CACHE){
                NULLABLE_FIELD_CACHE.put(field, annotation == null);
            }
        }
        return NULLABLE_FIELD_CACHE.get(field);
    }

    public String getCanonicalName(Class clazz){
        String name = CANONICAL_NAME_CASHE.get(clazz);
        if (name == null){
            name = clazz.getCanonicalName();
            synchronized (CANONICAL_NAME_CASHE){
                CANONICAL_NAME_CASHE.put(clazz, name);
            }
        }
        return name;
    }


}
