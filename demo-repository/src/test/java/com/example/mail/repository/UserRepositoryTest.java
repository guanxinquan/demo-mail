package com.example.mail.repository;

import com.example.mail.repository.model.User;

/**
 * Created by guanxinquan on 15-3-16.
 */
public class UserRepositoryTest {

    public static void main(String[] args){
        UserRepository userRepository = RepositoryFactory.getRepository(UserRepository.class);
        User user = new User();
        //user.setName();

        userRepository.createUser(new User());
    }
}
