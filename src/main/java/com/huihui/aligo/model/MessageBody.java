package com.huihui.aligo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息体
 *
 * @author minghui.y
 * @create 2021-01-30 11:34 下午
 **/
@Data
public class MessageBody implements Serializable {

    private Long messageId;

    private String name;

    private Integer age;

}
