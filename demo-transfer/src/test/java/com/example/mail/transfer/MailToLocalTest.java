package com.example.mail.transfer;

import com.example.mail.mq.model.MailInfo;

import java.util.Collections;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class MailToLocalTest {

    public static void main(String[] args){
        MailTransfer transfer = new MailTransfer();
        MailInfo mailInfo = new MailInfo();
        mailInfo.setFrom("guanxinquan@g.com");
        mailInfo.setTos(Collections.singletonList("guanxinquan@gxq.com"));
        mailInfo.setMessage("X-FOXMAIL-CODE: foxmail_code:\n" +
                "From: \"=?utf-8?B?Z3VhbnhpbnF1YW4=?=\"<guanxinquan@gxq.com>\n" +
                "To: \"=?utf-8?B?Z3VhbnhpbnF1YW4=?=\"<guanxinquan@g.com>\n" +
                "Subject: test email\n" +
                "Mime-Version: 1.0\n" +
                "Content-Type: text/html;\n" +
                "\tcharset=\"utf-8\"\n" +
                "Content-Transfer-Encoding: base64\n" +
                "Date: Wed, 11 Mar 2015 16:41:37 +0800\n" +
                "Message-ID: <tencent_CCD3A60154B4C6422E6350EC@qq.com>\n" +
                "X-QQ-MIME: TCMime 1.0 by Tencent\n" +
                "X-Mailer: Foxmail_for_Mac 1.0.0\n" +
                "\n" +
                "PGRpdiBzdHlsZT0iZm9udDoxNHB4LzEuNSAnTHVjaWRhIEdyYW5kZScsICflvq7ova/pm4Xp\n" +
                "u5EnO2NvbG9yOiMzMzM7Ij48cCBzdHlsZT0iZm9udDoxNHB4LzEuNSAnTHVjaWRhIEdyYW5k\n" +
                "ZSc7bWFyZ2luOjA7Ij50aGlzIGlzIGEgdGVzdCBlbW1haWwgLHRlc3QgZm9yIGVyeWRrZmph\n" +
                "OzwvcD48L2Rpdj4=");
        transfer.transfer(mailInfo);
    }
}
