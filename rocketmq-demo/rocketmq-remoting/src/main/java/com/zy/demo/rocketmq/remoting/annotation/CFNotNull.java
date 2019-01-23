package com.zy.demo.rocketmq.remoting.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CFNotNull {
}
