package com.example.springbootdemo.action;

import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.Util.DouYinShareUtil;
import com.example.springbootdemo.bean.Uid;
import com.example.springbootdemo.bean.User;
import com.example.springbootdemo.service.UidService;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
public class UidController {
   @Autowired
   private UidService uidService;
   @Autowired
   private UserService userService;

    @GetMapping("/uids")
    public String list(@PageableDefault(size = 100,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable,
                       Model model) {
        //sList<Book> books = bookService.findAll();
        Page<Uid> page1 = uidService.findAll(pageable);
        model.addAttribute("page", page1);
        return "uids";
    }


    @GetMapping("/uids/select")
    public String listWork() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        List<Uid> uids= uidService.findAll();
        int num = uids.size();
        for (int j = 1; j < 101; j++) {
            for (int i =1;i<num+1;i++){
                Uid uid = uidService.findById(i);
                String a = uid.getK();
                JSONObject jsonObject = null;
                try {
                    jsonObject = DouYinShareUtil.instance().getDouyinInfo(a);
                    if (jsonObject.size()>0){
                        User user = new User();
                        user.setId(i);
                        user.setName(a);
                        user.setHeadUrl(jsonObject.getString("headUrl"));
                        user.setNickName(jsonObject.getString("nickName"));
                        user.setDouYinId(jsonObject.getString("id"));
                        user.setFans(jsonObject.getString("fans"));
                        user.setFocus(jsonObject.getString("focus"));
                        user.setLiked(jsonObject.getString("liked"));
                        user.setSign(jsonObject.getString("sign"));
                        user.setWorks(jsonObject.getString("works"));
                        userService.save(user);
                        Thread.sleep(10);
                        System.out.println("----第"+i+"条数据----"+"共"+num+"条数据----第"+j+"次循环----");
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
        }

        }
        return "refreshSuccess";
    }
}


