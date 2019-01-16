package com.zy.demo.rocketmq.store;

import com.zy.demo.rocketmq.common.TopicFilterType;
import com.zy.demo.rocketmq.common.message.MessageExt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageExtBrokerInner extends MessageExt {
    private String propertiesString;
    private long tagsCode;

    public static long tagsString2TagsCode(final TopicFilterType filterType, final String tags) {
        if (null == tags || tags.length() == 0) {
            return 0;
        }

        return tags.hashCode();
    }

    public static long tagsString2tagsCode(final String tags) {
        return tagsString2TagsCode(null, tags);
    }

}
