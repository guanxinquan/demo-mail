package com.example.mail.mq;

import com.example.mail.mq.model.MailInfo;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import java.util.Collections;

/**
 * Created by guanxinquan on 15-3-13.
 */
public class MailMqSendTest {

    public static void main(String[] args) throws InterruptedException {
        MailMq mq = MailMqFactory.getMailMq(MailMq.class);
        MailInfo inf = new MailInfo();
        inf.setFrom("woshiguanxinquan@qq.com");
        inf.setTos(Collections.singletonList("lixiaolong@163.com"));
        inf.setMessage("this is a simple message");
        mq.sendMessage(inf);

        MailMqFactory.close();
    }
}
