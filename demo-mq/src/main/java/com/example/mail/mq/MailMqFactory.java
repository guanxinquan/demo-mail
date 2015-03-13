package com.example.mail.mq;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guanxinquan on 15-3-12.
 */
public class MailMqFactory {

    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-mq-context.xml");


    public static final <T> T getMailMq(Class<T> mq){
        return  context.getBean(mq);
    }

    public static void close(){
        context.close();
    }
}
