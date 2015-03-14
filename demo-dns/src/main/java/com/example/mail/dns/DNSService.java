package com.example.mail.dns;

import com.example.mail.dns.model.MXRecord;

import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public interface DNSService {

    /**
     * dns list filter ,to remove dns not localhost
     * @param dns dns list want to filter
     * @return dns list in localhost
     */
    public List<String> isLocalDNS(List<String> dns);

    /**
     * defaultDomain
     * @param defaultDomain
     * @return
     */
    public String defaultDomain(String defaultDomain);

    /**
     * post master
     * @return
     */
    public String getPostMaster();

    /**
     * find mx by dns
     * @param dns
     * @return
     */
    public List<MXRecord> getMX(String dns);


    /**
     * find mx by dns
     * @param dns nds
     * @param withCache if true, DNSService my use cache else receive mx direct from remote DNS Service
     * @return
     */
    public List<MXRecord> getMX(String dns,boolean withCache);


    /**
     * 设置成功使用的dns名称
     * @param dns
     * @param MXName
     */
    public void setUsedMX(String dns,String MXName);


}
