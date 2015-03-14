package com.example.mail.repository;

import com.example.mail.repository.MailProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by guanxinquan on 15-3-14.
 */
public interface DNSRepository {

    @Insert("insert into dns(name,create_date) values (#{name},#{createDate})")
    public void createDNS(@Param("name")String name,@Param("createDate")Date createDate);

    @SelectProvider(type= MailProvider.class,method = "checkLocalDns")
    public List<String> checkLocalDns(List<String> dns);

    @Select("select name from dns where name = #{name}")
    public String selectDnsByName (String name);
}
