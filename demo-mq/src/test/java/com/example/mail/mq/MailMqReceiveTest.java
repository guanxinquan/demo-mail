package com.example.mail.mq;

import com.example.mail.mq.model.MailInfo;

/**
 * Created by guanxinquan on 15-3-13.
 */
public class MailMqReceiveTest {

    public static void main(String[] args) throws InterruptedException {
        MailMq mq = MailMqFactory.getMailMq(MailMq.class);

        while(true){
            Object obj = mq.receiveMessage();
            if(obj != null){
                MailInfo info = (MailInfo) obj;
                System.out.println(info.getMessage());
            }else{
                System.out.println(obj);
            }
            Thread.sleep(1000);
        }

    }
}
