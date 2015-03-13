package com.example.mail.utils;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class MailUtils {

    public static final String LINE_END = "\r\n";

    public static String parseDomain(String mailAddress){

        int start = mailAddress.indexOf('@');
        int end = mailAddress.indexOf("/",start);

        if(start > 0 && end > 0){
            return mailAddress.substring(start+1,end);
        }else if(start > 0){
            return mailAddress.substring(start+1);
        }
        return null;
    }


}
