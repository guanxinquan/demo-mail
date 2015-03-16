package com.example.mail.transfer;

import com.example.mail.mq.model.MailInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by guanxinquan on 15-3-16.
 */
public class TransferToRemoteTest {

    public static final void main(String[] args){
        MailInfo info = new MailInfo();
        info.setFrom("guanxinquan@g.com");
        info.setTos(Collections.singletonList("woshiguanxinquan@163.com"));
        info.setMessage("X-FOXMAIL-CODE: foxmail_code:\n" +
                "From: \"=?utf-8?B?Z3VhbnhpbnF1YW4=?=\"<guanxinquan@g.com>\n" +
                "To: \"=?utf-8?B?Z3VhbnhpbnF1YW4=?=\"<woshiguanxinquan@163.com>\n" +
                "Subject: test subject\n" +
                "Mime-Version: 1.0\n" +
                "Content-Type: text/html;\n" +
                "\tcharset=\"utf-8\"\n" +
                "Content-Transfer-Encoding: base64\n" +
                "Date: Wed, 11 Mar 2015 16:06:41 +0800\n" +
                "Message-ID: <tencent_1F17EEAA3A7F56C4E64132ED@qq.com>\n" +
                "X-QQ-MIME: TCMime 1.0 by Tencent\n" +
                "X-Mailer: Foxmail_for_Mac 1.0.0\n" +
                "\n" +
                "PGRpdiBzdHlsZT0iZm9udDoxNHB4LzEuNSAnTHVjaWRhIEdyYW5kZScsICflvq7ova/pm4Xp\n" +
                "u5EnO2NvbG9yOiMzMzM7Ij48cCBzdHlsZT0iZm9udDoxNHB4LzEuNSAnTHVjaWRhIEdyYW5k\n" +
                "ZSc7bWFyZ2luOjA7Ij50aGlzIGlzIGEgdGVzdCBzdWJqZWN0ICxwbGVhc2UgZm9yIGVranNk\n" +
                "O2ZqYTtkajwvcD48L2Rpdj4=\n");


        MailTransfer mailTransfer = new MailTransfer();
        mailTransfer.transfer(info);
    }
}
