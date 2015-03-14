package com.example.mail.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class MailProviderTest {

    public static void main(String[] args){
        MailProvider mailProvider  = new MailProvider();
        List<String> names = new ArrayList<String>();
        names.add("1223.com");
        names.add("6788.com");
        names.add("5657.com");
        System.out.println(mailProvider.checkLocalDns(names));
    }
}
