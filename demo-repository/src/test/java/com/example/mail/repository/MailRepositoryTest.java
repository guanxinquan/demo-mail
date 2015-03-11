package com.example.mail.repository;

import com.example.mail.repository.model.User;

/**
 * Created by guanxinquan on 15-3-11.
 */
public class MailRepositoryTest {

    public static void main(String[] args){
        UserRepository userRepository = RepositoryFactory.getRepository(UserRepository.class);
        User user = userRepository.selectUserByName("guanxinquan@gxq.com");
        System.out.println(user.getName());

    }

}
