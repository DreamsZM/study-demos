package com.zy.demo.rocketmq.common;

import com.zy.demo.rocketmq.remoting.protocol.RemotingSerializable;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class DataVersion extends RemotingSerializable {
    private long timestamp = System.currentTimeMillis();
    private AtomicLong counter = new AtomicLong(0);

    /**
     * TODO：为什么不用构造函数？？？
     * count没有直接赋值，浅拷贝 & 深拷贝
     * 值传递，非引用传递
     * @param dataVersion
     */
    public void assignNewOne(DataVersion dataVersion){
        this.timestamp = dataVersion.timestamp;
        this.counter.set(dataVersion.counter.get());
    }

    /**
     * 获取下一个版本
     */
    public void nextVersion(){
        this.timestamp = System.currentTimeMillis();
        this.counter.incrementAndGet();
    }

    @Override
    public boolean equals(Object obj) {
        //是否是一个对象
        if (this == obj){
            return true;
        }
        //是否是一个类
        if (obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        //类转型
        final DataVersion that = (DataVersion) obj;
        //fields的值是否一样
        if (this.timestamp != that.timestamp){
            return false;
        }

        if (that.counter !=null && this.counter != null){
            return this.counter.longValue() == that.counter.longValue();
        }

        return (this.counter == null) && (this.counter == null);
    }

    /**
     * TODO:
     * @return
     */
    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        if (null != counter) {
            long l = counter.get();
            result = 31 * result + (int) (l ^ (l >>> 32));
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataVersion[");
        sb.append("timestamp=").append(timestamp);
        sb.append(", counter=").append(counter);
        sb.append(']');
        return sb.toString();
    }
}
