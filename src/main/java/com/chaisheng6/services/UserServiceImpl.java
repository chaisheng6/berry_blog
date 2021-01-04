package com.chaisheng6.services;

import com.chaisheng6.dao.UserRepository;
import com.chaisheng6.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username,password);
        return user;
    }
}
