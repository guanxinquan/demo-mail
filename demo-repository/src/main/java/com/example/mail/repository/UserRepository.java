package com.example.mail.repository;

import com.example.mail.repository.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

/**
 * Created by guanxinquan on 15-3-11.
 */
public interface UserRepository {


    @Insert("insert into user (name,password) values (#{name},#{password}")
    @SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS id")
    public Integer createUser(User user);

    @Select("select id,name,password from user where name = #{name}")
    public User selectUserByName(String name);

    @Update("update user set password = #{password} where name=#{name} ")
    public void changePassword(@Param("name")String name,@Param("password")String password);

    @Select("select id from user where name=#{name} and password=#{password}")
    public Integer verifyUser(@Param("name")String userName, @Param("password")String password);
}
