package com.example.springbootdemo.business;

import com.example.springbootdemo.bean.Book;
import com.example.springbootdemo.bean.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBussiness {
    @Autowired
    private UserService userService;

    //新增抖音用户
    public User save(User user){
        return userService.save(user);
    }



    //查询所有信息
    List<User> findAll(){
        return userService.findAll();
    }
}
