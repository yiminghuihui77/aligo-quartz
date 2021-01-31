package com.huihui.aligo.constant;

/**
 * @author minghui.y
 * @create 2021-01-30 11:47 下午
 **/
public enum MessageResult {

    SUCCESS("消费成功"),
    FAIL("消费失败");


    private String name;

    MessageResult(String name) {
        this.name = name;
    }

}
