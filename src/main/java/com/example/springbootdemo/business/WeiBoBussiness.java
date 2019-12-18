package com.example.springbootdemo.business;

import com.example.springbootdemo.bean.UserModel;
import com.example.springbootdemo.service.WeiBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeiBoBussiness {
    @Autowired
    private WeiBoService weiBoService;

    public UserModel save(UserModel userModel){
        return weiBoService.save(userModel);
    }
}
