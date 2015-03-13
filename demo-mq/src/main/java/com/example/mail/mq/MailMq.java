package com.example.mail.mq;

import com.example.mail.mq.model.MailInfo;

/**
 * Created by guanxinquan on 15-3-13.
 */
public interface MailMq {


    public void sendMessage(Object Message);

    public Object receiveMessage();

}
