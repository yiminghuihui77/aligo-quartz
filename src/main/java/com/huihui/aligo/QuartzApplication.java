package com.huihui.aligo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author minghui.y
 * @create 2021-01-16 12:49 下午
 **/
@SpringBootApplication
public class QuartzApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger( QuartzApplication.class );

    public static void main( String[] args ) {
        SpringApplication.run( QuartzApplication.class );
        LOGGER.info( "Quartz Application start success ..." );
    }
}
