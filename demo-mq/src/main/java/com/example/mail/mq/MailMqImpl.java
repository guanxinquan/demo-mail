package com.example.mail.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by guanxinquan on 15-3-13.
 */
public class MailMqImpl implements MailMq{

    AmqpTemplate amqpTemplate;

    CachingConnectionFactory connectionFactory;



    private String queueName;

    private String exchangeName;

    @Override
    public void sendMessage(Object message) {
        amqpTemplate.convertAndSend(exchangeName,null,message);
    }

    @Override
    public Object receiveMessage() {

        //return amqpTemplate.receive(queueName);
        return amqpTemplate.receiveAndConvert(queueName);
    }


    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }


    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }


    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setConnectionFactory(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}
