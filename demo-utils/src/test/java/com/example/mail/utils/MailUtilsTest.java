package com.example.mail.utils;

/**
 * Created by guanxinquan on 15-3-13.
 */
public class MailUtilsTest {

    public static void main(String[] args){
        System.out.println(MailUtils.parseDomain("woshiguanxinquan@163.com/index"));
        System.out.println(MailUtils.parseDomain("woshiguanxinquan@163.com"));
    }
}
