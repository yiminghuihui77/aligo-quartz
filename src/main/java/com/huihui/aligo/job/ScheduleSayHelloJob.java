package com.huihui.aligo.job;

import com.huihui.aligo.service.SayHelloService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时sayHello的job
 * QuartzJobBean是org.quartz.Job的抽象实现类
 * @author minghui.y
 * @create 2021-01-16 1:14 下午
 **/
public class ScheduleSayHelloJob extends QuartzJobBean {

    /**
     * 注意：job本身并没有显示的使用@Component注解注入到IOC中
     * 但是却可以使用@Autowired完成DI
     * spring-boot-starter-quartz依赖会自动配置：QuartzAutoConfiguration
     */
    @Autowired
    private SayHelloService sayHelloService;

    @Override
    protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
        sayHelloService.sayHello();
    }
}
