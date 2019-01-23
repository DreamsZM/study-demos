package com.zy.demo.rocketmq.namesrv.routeInfo;

import com.zy.demo.rocketmq.common.DataVersion;
import com.zy.demo.rocketmq.common.namesrv.RegisterBrokerResult;
import com.zy.demo.rocketmq.common.protocol.route.BrokerData;
import com.zy.demo.rocketmq.common.protocol.route.QueueData;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteInfoManager {
    /**
     * channel 超时时间
     */
    private static final int BROKER_CHANNEL_EXPIRED_TIME = 1000 * 60 * 2;

    /**
     * topic queue
     * topicName
     */
    private final Map<String, List<QueueData>> topicQueueTable;

    /**
     * broker
     * brokerName,
     */
    private final Map<String, BrokerData> brokerAddrTable;

    /**
     * <clusterName
     *
     */
    private final Map<String, Set<String>> clusterAddrTable;

    /**
     * <brokeAddr
     */
    private final Map<String, BrokerLiveInfo> brokerLiveTable;

    /**
     * <brokerAddr
     * filterServer>
     */
    private final Map<String, List<String>> filterServerTable;

    public RouteInfoManager() {
        topicQueueTable = new HashMap<>(1024);
        brokerAddrTable = new HashMap<>(128);
        clusterAddrTable = new HashMap<>(32);
        brokerLiveTable = new HashMap<>(256);
        filterServerTable = new HashMap<>(256);
    }

    /**
     * 获取clusterInfo
     * @return
     */
    public byte[] getAllClusterInfo(){

    }

    public void deleteTopic(final String topic){

    }

    public byte[] getAllTopicList(){

    }


    public RegisterBrokerResult registerBroker(){
        RegisterBrokerResult result = new RegisterBrokerResult();
        return result;
    }

    public boolean isBrokerTopicConfigChanged(){

    }

    public DataVersion queryBrokerTopicConfig(final String brokerAddr){

    }

    public void updateBrokerInfoUpdateTimestamp(final String brokerAddr){

    }

    private void createAndUpdateQueueData(final String brokerName){

    }
}

@Getter
@Setter
class BrokerLiveInfo{
    private long lastUpdateTimestamp;
    private DataVersion dataVersion;
    private Channel channel;
    private String haServerAddr;

    public BrokerLiveInfo(long lastUpdateTimestamp, DataVersion dataVersion,
                          Channel channel, String haServerAddr) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.dataVersion = dataVersion;
        this.channel = channel;
        this.haServerAddr = haServerAddr;
    }

    @Override
    public String toString() {
        return "BrokerLiveInfo [lastUpdateTimestamp=" + lastUpdateTimestamp + ", dataVersion=" + dataVersion
                + ", channel=" + channel + ", haServerAddr=" + haServerAddr + "]";
    }
}
