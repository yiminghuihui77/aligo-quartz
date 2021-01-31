package com.huihui.aligo.mapper;

import com.huihui.aligo.model.MessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author minghui.y
 * @create 2021-01-30 11:19 下午
 **/
@Mapper
public interface MessageRecordMapper {

    /**
     * 插入
     * @param messageRecord
     * @return
     */
    int insert( MessageRecord messageRecord );
    
    /**
     * 更新
     * @param messageRecord
     * @return
     */
    int update(MessageRecord messageRecord);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    MessageRecord getRecordById(@Param( "id" ) long id);

    /**
     * 根据消息ID查询
     * @param messageId
     * @return
     */
    MessageRecord getRecordByMessageId(@Param( "messageId" ) long messageId);

    /**
     * 查询失败的需要重试的记录
     * @return
     */
    List<MessageRecord> searchFailRecords();
}
