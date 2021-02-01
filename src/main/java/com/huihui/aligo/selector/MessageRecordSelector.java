package com.huihui.aligo.selector;

import com.huihui.aligo.annotation.EnableMessageRecorder;
import com.huihui.aligo.aspect.MessageRecorderAspect;
import com.huihui.aligo.configuration.QuartzConfiguration;
import com.huihui.aligo.model.MessageBody;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 搭配@Import注解使用
 * @author minghui.y
 * @create 2021-02-01 9:11 下午
 **/
public class MessageRecordSelector implements ImportSelector {

    @Override
    public String[] selectImports( AnnotationMetadata importingClassMetadata ) {
        Map<String, Object> attributeMap = importingClassMetadata.getAnnotationAttributes( EnableMessageRecorder.class.getName() );
       String cron = (String) attributeMap.get( "cron" );
        System.out.println("@EnableMessageRecorder注解的cron的值：" + cron);
        return new String[]{QuartzConfiguration.class.getName(), MessageRecorderAspect.class.getName()};
    }
}
