package com.example.springbootdemo.service;

import com.example.springbootdemo.bean.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookService extends JpaRepository<Book,Long> {

    Page<Book> findAll(Pageable pageable);

    List<Book> findByAuthor(String author); //单个条件查询

    List<Book> findByAuthorAndStatus(String author,int status);//多个条件查询

    List<Book> findByDescriptionEndsWith(String description);//以什么为结尾查询

    List<Book> findByDescriptionContains(String description);// %like%查询



   // @Query("select b from Book b where length(b.name)> ?1")
    //List<Book> findByJPQL(int len);
    @Query(value = "select * from Book  where length(name)>?1",nativeQuery = true)
    List<Book> findByJPQL(int len);

    @Transactional
    @Modifying
    @Query("update Book b set b.status = ?1 where id = ?2")
    int updateByJPQL(int status,long id);

    @Transactional
    @Modifying
    @Query("delete from Book b where b.id =?1")
    int deleteByJPQL(long id);

}
