package com.example.springbootdemo.service;

import com.example.springbootdemo.bean.Uid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UidService extends JpaRepository<Uid,Long> {

    Page<Uid> findAll(Pageable pageable);

    Uid findById(long id);

}
