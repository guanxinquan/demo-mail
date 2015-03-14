package com.example.mail.dns;

import com.example.mail.dns.model.MXRecord;

import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class DNSServiceTest {

    public static void main(String[] args){
        DNSService service = DNSServiceFactory.getDNSService();

        List<MXRecord> mxRecords = service.getMX("qq.com");

        for(MXRecord mxRecord : mxRecords){
            System.out.println(mxRecord.getName());
        }
    }
}
