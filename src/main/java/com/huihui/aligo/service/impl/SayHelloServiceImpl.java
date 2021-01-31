package com.huihui.aligo.service.impl;

import com.huihui.aligo.service.SayHelloService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author minghui.y
 * @create 2021-01-16 2:11 下午
 **/
@Service
public class SayHelloServiceImpl implements SayHelloService {


    @Override
    public void sayHello() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println( "say hello to context:"  + formatter.format( new Date() ));
    }
}
