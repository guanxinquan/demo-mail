package com.example.mail.utils;

import java.io.BufferedReader;
import java.io.StringReader;

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

    public static String readHeader(String data){
        StringReader sr = new StringReader(data);

        String line = null;
        BufferedReader br = new BufferedReader(sr);

        StringBuilder header = new StringBuilder();

        try {
            while ((line = br.readLine()) != null) {
                header.append(line+LINE_END);
                if("".equals(line)){
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return header.toString();
    }

}
