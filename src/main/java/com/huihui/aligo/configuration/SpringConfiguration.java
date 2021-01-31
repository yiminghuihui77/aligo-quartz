package com.huihui.aligo.configuration;

import com.huihui.aligo.utils.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author minghui.y
 * @create 2021-01-31 3:17 下午
 **/
@Configuration
public class SpringConfiguration {

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

}
