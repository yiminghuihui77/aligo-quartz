package com.huihui.aligo.controller;

import com.alibaba.fastjson.JSONObject;
import com.huihui.aligo.annotation.MessageRecorder;
import com.huihui.aligo.model.MessageBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author minghui.y
 * @create 2021-01-30 9:49 下午
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {


    @GetMapping("/sayHello")
    @MessageRecorder( retryTimes = 5, emailAlarm = true, emailAddress = "yinminghui343@hellobike.com")
    public String sayHello( MessageBody messageBody ) {

        int i = 1 / messageBody.getAge();
        return JSONObject.toJSONString( messageBody );
    }
}
