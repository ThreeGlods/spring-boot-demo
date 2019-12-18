package com.example.springbootdemo.action;

import com.example.springbootdemo.bean.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String list(@PageableDefault(size = 100,sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable,
                       Model model) {
        //sList<Book> books = bookService.findAll();
        Page<User> page1 = userService.findAll(pageable);
        model.addAttribute("page", page1);
        return "users";
    }

    @GetMapping("/users/works")
    public String listWork(@PageableDefault(size = 100,sort = {"works"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        //sList<Book> books = bookService.findAll();
        Page<User> page1 = userService.findAll(pageable);
        model.addAttribute("page", page1);
        return "works";
    }
}
