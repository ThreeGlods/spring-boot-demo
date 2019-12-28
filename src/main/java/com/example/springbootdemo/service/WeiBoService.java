package com.example.springbootdemo.service;

import com.example.springbootdemo.bean.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeiBoService extends JpaRepository<UserModel, Long> {

    Page<UserModel> findAll(Pageable pageable);

}
