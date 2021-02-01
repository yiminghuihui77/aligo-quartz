package com.huihui.aligo.annotation;

import com.huihui.aligo.selector.MessageRecordSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用消息记录注解
 *
 * @author minghui.y
 * @create 2021-02-01 9:06 下午
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(MessageRecordSelector.class)
public @interface EnableMessageRecorder {

    String cron() default "";
}
