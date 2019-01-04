package com.zy.demo.rocketmq.remoting.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pair<T1, T2> {
    private T1 object1;
    private T2 object2;

    public Pair(T1 object1, T2 object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

}
