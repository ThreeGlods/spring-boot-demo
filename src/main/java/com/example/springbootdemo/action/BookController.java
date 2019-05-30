package com.example.springbootdemo.action;

import com.example.springbootdemo.bean.Book;
import com.example.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5")int size,
                       Model model) {
        //sList<Book> books = bookService.findAll();
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Page<Book> page1 = bookService.findAll(PageRequest.of(page,size,sort));
        model.addAttribute("page", page1);
        System.out.println("这是什么："+page1.getContent());
        return "books";
    }

    /*
     * 跳转到input提交页面
     * */
    @GetMapping("/books/input")
    public String inputPage(Model model) {
        model.addAttribute("book", new Book());
        return "input";
    }


    @GetMapping("/books/{id}")
    public String detail(@PathVariable long id, Model model) {
        Book book = bookService.findById(id).orElse(null);
        if (book == null) {
            book = new Book();
        }
        model.addAttribute("book", book);
        return "book";
    }


    /*
     * 跳转到更新页面
     * */

    @GetMapping("/books/{id}/input")
    public String inputEditPage(@PathVariable long id, Model model) {
        Book book = bookService.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "input";
    }

    /*
     * 提交书单信息
     * */
    @PostMapping("/books")
    public String post(Book book, final RedirectAttributes attributes) {
        Book book1 = bookService.save(book);
        if (book1 != null) {
            attributes.addFlashAttribute("message", "《" + book1.getName() + "》信息提交成功");
        }
        return "redirect:books";
    }
    /*
     * POST---> redirect--->GET
     * */

}
