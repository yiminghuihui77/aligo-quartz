package com.huihui.aligo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 递增job
 *
 * @author minghui.y
 * @create 2021-01-16 1:20 下午
 **/
public class ScheduleIncreaseJob extends QuartzJobBean {

    /**
     * 如果count是实例属性，则打印的值永远为0
     * 说明每次trigger出发执行job是，都是重新实例化一个job?
     */
    private static int count;

    @Override
    protected void executeInternal( JobExecutionContext jobExecutionContext ) throws JobExecutionException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println( "count :"  + count++ + "--" + formatter.format( new Date() ));

    }
}
