package com.example.mail.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class RepositoryFactory {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-repository-context.xml");

    public static final <T> T getRepository(Class<T> repository){
        return  context.getBean(repository);
    }
}
