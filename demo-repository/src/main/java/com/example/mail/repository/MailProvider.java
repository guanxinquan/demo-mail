package com.example.mail.repository;

import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class MailProvider {

    public String checkLocalDns(List<String> dnsList){
        StringBuilder listBuilder = new StringBuilder();
        for (String dns : dnsList){
            listBuilder.append(dns);
            listBuilder.append(",");
        }
        return "select name from dns where name in ("+listBuilder.substring(0,listBuilder.length()-1)+")";
    }

}
