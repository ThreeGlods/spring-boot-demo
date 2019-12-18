package com.example.springbootdemo.business;

import com.example.springbootdemo.bean.Uid;
import com.example.springbootdemo.service.UidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UidBussiness {
    @Autowired
    private UidService uidService;


    public List<Uid> findAll(){
        return uidService.findAll();
    }

}
