>>切面：MessageRecorderAspect  
插入消费消息的记录：时间，消息体(json)、全称类名、方法名、业务场景（业务线）

>>定时job： MessageRetryJob  
查询失败的消费记录，通过反射的方式调用，更新消费记录


问题：
1、定时job中，如果通过反射调用目标方法，则需要实例对象，这个实例对象不能反射，应该从SpringContext中获取
2、如果实例（代理）对象从SpringContext中获取，则会走切面逻辑，因此切面逻辑中需要判断当前消费是否存在记录，存在则更新，不存在则创建
3、消息体中需要存在一个唯一标识，messageId

限制：
1、消息消费者由SpringIOC管理（这个一般都能满足）
2、消息体对象都是统一的，或者表中存储消息体的全称类名;消费的方法的参数只能有一个，即消费体
3、每个消息能够被唯一标识，如messageId
4、消费方法的异常不能catch掉（否则需要对方法返回值进行判断是否执行成功，要求方法返回值统一）



提供SDK：
job执行时间s
job组、job标识、trigger组、trigger标识（提供默认的）


数据表设计：
guid(id)、class_name、method_name、message_id、message_body、
execute_result、execute_count、execute_threshold、email_alarm、email_address



