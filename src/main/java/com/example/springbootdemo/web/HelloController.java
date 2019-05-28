package com.example.springbootdemo.web;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
//@Controller
@RequestMapping("/api/v1")
public class HelloController {

   // @RequestMapping(value = "/say",method = RequestMethod.POST)
    @PostMapping("/say")

    public String hello(){
        return "Hello Spring Boot";
    }
    @GetMapping("/books")
    //@ResponseBody
    public Object getAll(){
        Map<String, Object> map =new HashMap<>();
        map.put("name","hello");
        map.put("age","18");
        return map;
    }
    @GetMapping("/books/{id}/{username:[a-z_]+}")
    public Object getOne(@PathVariable long id,@PathVariable String username){

        System.out.println("id:"+id+"---"+"username:"+username);
        Map<Object, Object> book = new HashMap<>();
        book.put("name","互联网世界观");
        book.put("isbn","2523243");
        book.put("author","井鑫");
        book.put("username",username);
        return book;
    }
}
