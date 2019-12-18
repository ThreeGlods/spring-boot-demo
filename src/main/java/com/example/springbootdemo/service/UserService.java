package com.example.springbootdemo.service;

import com.example.springbootdemo.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService  extends JpaRepository<User,Long> {

    Page<User> findAll(Pageable pageable);


}
