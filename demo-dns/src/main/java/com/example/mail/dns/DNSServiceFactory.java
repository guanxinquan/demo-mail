package com.example.mail.dns;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class DNSServiceFactory {

    public static DNSService getDNSService(){
        return new DNSServiceImpl();
    }

}
