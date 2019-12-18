package com.example.springbootdemo.business;

import com.example.springbootdemo.bean.Book;
import com.example.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookBusiness {
    @Autowired
    private BookService bookService;

    /*
    * 查询所有信息
    * */
    public List<Book> findAll(){

        return bookService.findAll();
    }


    /*
    * 新增一个书单
    * */
    public Book save(Book book){

        return bookService.save(book);
    }


    public Optional<Book> findOne(long id){
        return bookService.findById(id);
    }

    public void delteOne( long id){
        bookService.deleteById(id);
    }

    public List<Book> findByAuthor(String author){
        return bookService.findByAuthor(author);
    }

    public List<Book> findByAuthorAndStatus(String author,int status){
        return bookService.findByAuthorAndStatus(author,status);
    }

    public List<Book> findByDescriptionEndsWith(String description){
        return bookService.findByDescriptionEndsWith(description);
    }

    public List<Book> findByDescriptionContains(String description){
        return bookService.findByDescriptionContains(description);
    }
/*
* 根据自定义查询
* */
    public List<Book> findByJPQL(int len){

        return bookService.findByJPQL(len);
    }

    /*
     * 分页查询书单列表
     * */

    public Page<Book> findAllByPage(Pageable pageable){

        return bookService.findAll(pageable);
    }


    /*
     * 自定义更新
     * */

    @Transactional
    public int updateByJPQL(int status,long id){
        return bookService.updateByJPQL(status,id);
    }

    /*
    * 自定义删除
    * */
    @Transactional
    public int deleteByJPQL(long id){
        return bookService.deleteByJPQL(id);
    }


}
