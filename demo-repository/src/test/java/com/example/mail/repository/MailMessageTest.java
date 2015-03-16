package com.example.mail.repository;

import com.example.mail.repository.model.Message;

import java.util.Date;

/**
 * Created by guanxinquan on 15-3-16.
 */
public class MailMessageTest {

    public static final void main(String[] args){
        MessageRepository messageRepository = RepositoryFactory.getRepository(MessageRepository.class);

        Long id = messageRepository.createMessage(new Message("header","body"));//("header","body",new Date());

        System.out.println(id);

    }
}
