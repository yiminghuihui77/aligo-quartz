package com.huihui.aligo.job;

import com.alibaba.fastjson.JSONObject;
import com.huihui.aligo.mapper.MessageRecordMapper;
import com.huihui.aligo.model.MessageBody;
import com.huihui.aligo.model.MessageRecord;
import com.huihui.aligo.utils.SpringContextUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 消息重试job
 *
 * @author minghui.y
 * @create 2021-01-31 11:02 上午
 **/
public class MessageRetryJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger( MessageRetryJob.class );

    @Autowired
    private MessageRecordMapper messageRecordMapper;


    @Override
    protected void executeInternal( JobExecutionContext jobExecutionContext ) throws JobExecutionException {
        //查询需要重试的消费记录
        List<MessageRecord> messageRecords = messageRecordMapper.searchFailRecords();

        messageRecords.forEach( record -> {
            String className = record.getClassName();
            String methodName = record.getMethodName();
            String message = record.getMessage();

            //反射
            try {

                LOGGER.info( "消息重试中.." );
                Class<?> clazz = Class.forName( className );
                Method method = clazz.getDeclaredMethod( methodName, MessageBody.class );
                method.invoke( SpringContextUtil.getBean( clazz ), JSONObject.parseObject( message, MessageBody.class ) );

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error( "消息重试失败..." );
            }

        } );
    }
}
