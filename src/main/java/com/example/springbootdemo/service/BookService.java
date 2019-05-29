package com.example.springbootdemo.service;

import com.example.springbootdemo.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookService extends JpaRepository<Book,Long> {
    List<Book> findByAuthor(String author); //单个条件查询

    List<Book> findByAuthorAndStatus(String author,int status);//多个条件查询

    List<Book> findByDescriptionEndsWith(String description);//以什么为结尾查询

    List<Book> findByDescriptionContains(String description);// %like%查询

    @Query("select b from Book b where length(b.name)> ?1")
    List<Book> findByJPQL(int len);
}
