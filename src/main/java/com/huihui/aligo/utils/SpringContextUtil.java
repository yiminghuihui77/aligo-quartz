package com.huihui.aligo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring上下文工具类
 *
 * @author minghui.y
 * @create 2021-01-31 3:13 下午
 **/
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean( beanName );
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean( clazz );
    }
}
