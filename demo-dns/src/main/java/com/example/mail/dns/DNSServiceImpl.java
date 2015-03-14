package com.example.mail.dns;

import com.example.mail.dns.model.MXRecord;
import com.example.mail.repository.DNSRepository;
import com.example.mail.repository.RepositoryFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by guanxinquan on 15-3-14.
 */
public class DNSServiceImpl implements DNSService{

    private static Map<String,List<MXRecord>> caches = new ConcurrentHashMap<String, List<MXRecord>>();

    private static DNSRepository dnsRepository = RepositoryFactory.getRepository(DNSRepository.class);

    private static final String DEFAULT_DOMAIN = "gxq.com";

    private static final String POST_MASTER = "guanxinquan@gxq.com";


    @Override
    public List<String> isLocalDNS(List<String> dns) {
        return dnsRepository.checkLocalDns(dns);
    }

    @Override
    public String defaultDomain(String defaultDomain) {
        return DEFAULT_DOMAIN;
    }

    @Override
    public String getPostMaster() {
        return POST_MASTER;
    }

    @Override
    public List<MXRecord> getMX(String dns) {
        return getMX(dns,true);
    }

    @Override
    public List<MXRecord> getMX(String dns, boolean withCache) {
        List<MXRecord> mxRecords;
        if(withCache){
            mxRecords = caches.get(dns);
            if(mxRecords != null)
                return mxRecords;
        }

        try {
            Record[] records = new Lookup(dns, Type.MX).run();
            if(records != null && records.length > 0){
                mxRecords = new ArrayList<MXRecord>();
                for(Record record : records){
                    org.xbill.DNS.MXRecord r = (org.xbill.DNS.MXRecord) record;

                    String target = r.getTarget().toString();

                    MXRecord mxRecord = new MXRecord(target.substring(0,target.length() -1),r.getPriority(),false);
                    mxRecords.add(mxRecord);
                }
                caches.put(dns,mxRecords);
                return mxRecords;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void setUsedMX(String dns, String MXName) {
        List<MXRecord> records = caches.get(dns);
        if(records != null){
            for(MXRecord record : records){
                if(record.equals(dns)){
                    record.setChecked(true);
                }
            }

        }
    }
}
