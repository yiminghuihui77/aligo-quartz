package com.huihui.aligo.annotation;

import java.lang.annotation.*;

/**
 * 消息消费记录
 *
 * @author minghui.y
 * @create 2021-01-30 4:21 下午
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MessageRecorder {

    /**
     * 重试次数
     * @return
     */
    int retryTimes() default 5;

    /**
     * 是否邮件告警
     * @return
     */
    boolean emailAlarm() default false;

    /**
     * 告警邮件地址
     * 多个地址用","分割
     * @return
     */
    String emailAddress() default "";
}
