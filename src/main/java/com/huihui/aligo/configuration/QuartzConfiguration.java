package com.huihui.aligo.configuration;

import com.huihui.aligo.job.MessageRetryJob;
import com.huihui.aligo.job.ScheduleIncreaseJob;
import com.huihui.aligo.job.ScheduleSayHelloJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * Quartz配置类
 *
 * @author minghui.y
 * @create 2021-01-16 1:22 下午
 **/
//@Configuration
public class QuartzConfiguration {

    @Value( "${retry.cron}" )
    private String retryCron;

    /**********ScheduleSayHelloJob的配置****************/

//    @Bean
    public JobDetail sayHelloJobDetail() {
        return JobBuilder.newJob( ScheduleSayHelloJob.class )
                .withIdentity( "sayHelloJob", "group1" )
                .withDescription( "我是一个say hello的job" )
                .storeDurably()
                .build();
    }

//    @Bean
    public Trigger simpleTrigger4SayHello() {
        long time = System.currentTimeMillis() + 10 * 1000;
        Date startTime = new Date(time);

        return TriggerBuilder.newTrigger()
                .withIdentity( "simple trigger", "group3" )
                .withDescription( "我是一个简单的trigger" )
                .forJob( sayHelloJobDetail() )
                .withPriority( 1 )
                .startAt( startTime )
                //每5秒执行一次，重复执行
                .withSchedule( SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds( 5 ).repeatForever() )
                .build();
    }



    /**********ScheduleIncreaseJob的配置****************/

//    @Bean
    public JobDetail increaseJobDetail() {
        return JobBuilder.newJob( ScheduleIncreaseJob.class )
                .withIdentity( "increaseJob", "group2" )
                .withDescription( "我是一个 increase count 的job" )
                .storeDurably()
                .build();
    }

//    @Bean
    public Trigger cronTrigger4Increase() {
        return TriggerBuilder.newTrigger()
                .withIdentity( "cron trigger", "group4" )
                .withDescription( "我是一个执行cron的trigger" )
                .forJob( increaseJobDetail() )
                .withSchedule( CronScheduleBuilder.cronSchedule( "*/2 * * * * ?" ))
                .build();
    }

    /**********MessageRetryJob的配置****************/

    @Bean
    public JobDetail messageRetryJobDetail() {
        return JobBuilder.newJob( MessageRetryJob.class )
                .withIdentity( "messageRetryJob", "messageRetryJobGroup" )
                .withDescription( "消息重试job" )
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger messageRetryTrigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity( "messageRetry trigger", "messageRetry Trigger Group" )
                .withDescription( "消息重试trigger" )
                .forJob( messageRetryJobDetail() )
                //0 */2 * * * ?
                .withSchedule( CronScheduleBuilder.cronSchedule( retryCron ) )
                .build();
    }


}
