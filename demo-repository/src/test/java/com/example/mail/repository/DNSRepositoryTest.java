package com.example.mail.repository;

import java.util.Date;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class DNSRepositoryTest {

    public static void main(String[] args){
        DNSRepository dnsRepository = RepositoryFactory.getRepository(DNSRepository.class);
        dnsRepository.createDNS("gxq.com",new Date());
        dnsRepository.createDNS("g.com",new Date());
    }
}
