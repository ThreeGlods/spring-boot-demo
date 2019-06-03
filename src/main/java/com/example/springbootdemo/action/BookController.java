package com.example.springbootdemo.action;

import com.example.springbootdemo.bean.Book;
import com.example.springbootdemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.PublicKey;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        //sList<Book> books = bookService.findAll();
        Page<Book> page1 = bookService.findAll(pageable);
        model.addAttribute("page", page1);
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

    /*
    * 删除书单
    * */
    @GetMapping("/books/{id}/delete")
    public String delete(@PathVariable long id,final RedirectAttributes attributes){
        bookService.deleteById(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/books";
    }
    @GetMapping("/")
        public String login()    {

        return "redirect:/books";
    }
}
