package com.example.mail.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class RepositoryFactory {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

    public static final <T> T getRepository(Class<T> repository){
        T r =  context.getBean(repository);
        return  context.getBean(repository);
    }
}
