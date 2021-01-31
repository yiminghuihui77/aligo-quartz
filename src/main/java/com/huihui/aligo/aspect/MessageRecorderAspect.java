package com.huihui.aligo.aspect;

import com.alibaba.fastjson.JSONObject;
import com.huihui.aligo.annotation.MessageRecorder;
import com.huihui.aligo.constant.MessageResult;
import com.huihui.aligo.mapper.MessageRecordMapper;
import com.huihui.aligo.model.MessageBody;
import com.huihui.aligo.model.MessageRecord;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 消费记录切面
 * MessageRecorder注解的处理逻辑
 * @author minghui.y
 * @create 2021-01-30 4:55 下午
 **/
@Component
@Aspect
public class MessageRecorderAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger( MessageRecorderAspect.class );

    @Resource
    private MessageRecordMapper messageRecordMapper;

    @Pointcut("@annotation(com.huihui.aligo.annotation.MessageRecorder)")
    public void recordPointcut() {
    }

    /**
     * 抛出异常后增强
     */
    @AfterThrowing(value = "recordPointcut()", throwing = "ex")
    public void afterThrowingMethod( JoinPoint point, Exception ex ) throws NoSuchMethodException {
        //获取消费方法信息
        Class<?> clazz = point.getTarget().getClass();
        String methodName = point.getSignature().getName();
        Class<?>[] paramsType = ((MethodSignature) point.getSignature()).getParameterTypes();
        List<Object> args = Arrays.asList(point.getArgs());
        Method method = clazz.getMethod( methodName, paramsType );

        LOGGER.error( "消息异常处理，方法名称：{}, 参数：{}", methodName, args );

        MessageRecorder messageRecorder = method.getDeclaredAnnotation( MessageRecorder.class );

        Integer retryTimes = messageRecorder.retryTimes();
        boolean emailAlarm = messageRecorder.emailAlarm();
        String emailAddress = messageRecorder.emailAddress();
        //方法的信息
        String className = clazz.getTypeName();
        MessageBody messageBody = (MessageBody) args.get( 0 );

        //根据messageId查询消费记录
        MessageRecord exitRecord = messageRecordMapper.getRecordByMessageId( messageBody.getMessageId() );
        if (exitRecord == null) {
            //来自kafka消息调用
            MessageRecord messageRecord = new MessageRecord();
            messageRecord.setClassName( className );
            messageRecord.setMethodName( methodName );
            messageRecord.setMessage( JSONObject.toJSONString( messageBody ) );
            messageRecord.setCreatedTime( new Date() );
            messageRecord.setUpdatedTime( new Date() );
            messageRecord.setResult( MessageResult.FAIL );
            messageRecord.setExecuteCount( 1 );
            messageRecord.setExecuteThreshold( retryTimes );
            messageRecordMapper.insert( messageRecord );
        } else {
            //来自quartz重试调用
            exitRecord.setResult( MessageResult.FAIL );
            exitRecord.setExecuteCount( exitRecord.getExecuteCount() + 1 );
            messageRecordMapper.update( exitRecord );
        }



    }

    /**
     * 环绕增强
     */
    @Around( "recordPointcut()" )
    public void aroundMethod( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        //获取消费方法信息
        Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Class<?>[] paramsType = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterTypes();
        Method method = clazz.getMethod( methodName, paramsType );
        MessageRecorder messageRecorder = method.getDeclaredAnnotation( MessageRecorder.class );
        MessageBody messageBody = (MessageBody) proceedingJoinPoint.getArgs()[0];

        //消费消息前置处理
        LOGGER.info( "消息前置处理..., 方法名称：{}, 参数：{}", method.getName(), messageBody);

        //执行消费逻辑
        proceedingJoinPoint.proceed();

        //消费消息后置处理
        LOGGER.info( "消息后置处理..." );

        //根据messageId查询消费记录是否存在
        MessageRecord exitRecord = messageRecordMapper.getRecordByMessageId( messageBody.getMessageId() );
        if (exitRecord == null) {
            //来自kafka消息调用
            Integer retryTimes = messageRecorder.retryTimes();
            boolean emailAlarm = messageRecorder.emailAlarm();
            String emailAddress = messageRecorder.emailAddress();

            MessageRecord messageRecord = new MessageRecord();
            messageRecord.setClassName( clazz.getTypeName() );
            messageRecord.setMethodName( methodName );
            messageRecord.setMessage( JSONObject.toJSONString( messageBody ) );
            messageRecord.setCreatedTime( new Date() );
            messageRecord.setUpdatedTime( new Date() );
            messageRecord.setResult( MessageResult.SUCCESS );
            messageRecord.setExecuteCount( 1 );
            messageRecord.setExecuteThreshold( retryTimes );
            messageRecordMapper.insert( messageRecord );
        } else {
            //来自quartz重试调用
            exitRecord.setResult( MessageResult.SUCCESS );
            exitRecord.setExecuteCount( exitRecord.getExecuteCount() + 1 );
            messageRecordMapper.update( exitRecord );
        }

    }


}
