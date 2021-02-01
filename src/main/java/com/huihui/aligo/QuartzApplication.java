package com.huihui.aligo;

import com.huihui.aligo.annotation.EnableMessageRecorder;
import com.huihui.aligo.model.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 *
 * @author minghui.y
 * @create 2021-01-16 12:49 下午
 **/
@EnableMessageRecorder(cron = "huihui")
@SpringBootApplication
public class QuartzApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger( QuartzApplication.class );

    public static void main( String[] args ) {
        ConfigurableApplicationContext context = SpringApplication.run( QuartzApplication.class );
        LOGGER.info( "Quartz Application start success ..." );
    }
}
