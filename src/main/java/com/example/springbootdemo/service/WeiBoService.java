package com.example.springbootdemo.service;

import com.example.springbootdemo.bean.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeiBoService extends JpaRepository<UserModel, Long> {

}
