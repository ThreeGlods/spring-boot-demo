package com.example.springbootdemo.action;


import com.example.springbootdemo.bean.Book;
import com.example.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookApp {
    @Autowired
    private BookService bookService;

//    获取读书清单列表
    @GetMapping("/books")
    public List<Book> getAll(){
        return bookService.findAll();
    }
    @PostMapping("/books")
    public Book post(Book book){

       /* Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setStatus(status);
        */
        return bookService.save(book);
    }
/*
* 获取一条书单信息
* */
    @GetMapping("/books/{id}")

    public Optional<Book> getOne(@PathVariable long id){

        return bookService.findById(id);
    }
/*
* 更新一个书单
* */


    @PutMapping("/books")
    public Book update(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam String description,
            @RequestParam int status
    ){
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        book.setStatus(status);
        return bookService.save(book);
    }

    /*
    * 删除一个书单
    * */

    @DeleteMapping("/books/{id}")
    public  void deleteOne(@PathVariable long id){
        bookService.deleteById(id);
    }

    @PostMapping("/books/by")
    public List<Book> findByAuthor(@RequestParam String author){
        return bookService.findByAuthor(author);
    }

    @PostMapping("/books/by2")
    public List<Book> findByAuthorAndStatus(@RequestParam String author,
                                            @RequestParam int status
                                            ){
        return bookService.findByAuthorAndStatus(author,status);
    }

    @PostMapping("/books/by3")
    public List<Book> findByDescriptionEndsWith(@RequestParam String description
    ){
        return bookService.findByDescriptionEndsWith(description);
    }

    @PostMapping("/books/by4")
    public List<Book> findByDescriptionContains(@RequestParam String description
    ){
        return bookService.findByDescriptionContains(description);
    }

    @PostMapping("/books/by5")
    public List<Book> findByJPQL(@RequestParam int len
    ){
        return bookService.findByJPQL(len);
    }

}
