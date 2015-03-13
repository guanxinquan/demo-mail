package com.example.mail.repository;

import com.example.mail.repository.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * Created by guanxinquan on 15-3-12.
 */
public interface MessageRepository {

    @Insert("insert into message(header,body,create_date,update_date) values(#{header},#{body},#{createDate},#{updateData}) ")
    public void createMessage(
            @Param("header")String header,
            @Param("body")String body,
            @Param("createDate")Date createDate,
            @Param("updateDate")Date updateDate);

    @Select("select header from message where id=#{id}")
    public String selectHeaderById(Long id);

    @Select("select body from message where id=#{id}")
    public String selectBodyById(Long id);

    @Select("select id,header,body,create_date,update_date from message where id=#{id}")
    public Message selectMessageById(Long id);

}
