package com.huihui.aligo.model;

import com.huihui.aligo.constant.MessageResult;
import lombok.Data;

import java.util.Date;

/**
 * 消息记录model
 *
 * @author minghui.y
 * @create 2021-01-30 11:15 下午
 **/
@Data
public class MessageRecord {

    private long id;

    private String className;

    private String methodName;

    private long messageId;

    private String message;

    private Date createdTime;

    private Date updatedTime;

    private MessageResult result;

    private Integer executeCount;

    private Integer executeThreshold;
}
