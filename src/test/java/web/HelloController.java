package web;

import com.example.springbootdemo.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Object getAll(@RequestParam("page") int page,@RequestParam("size") int size){
        Map<Object, Object> book = new HashMap<>();
        book.put("name","互联网世界观");
        book.put("isbn","2523243");
        book.put("author","井鑫");
        Map<Object, Object> book2 = new HashMap<>();
        book2.put("name","打三个热嘎斯");
        book2.put("isbn","33352322443");
        book2.put("author","极品小黑");

        List<Map> contents = new ArrayList<>();
        contents.add(book);
        contents.add(book2);


        Map<String, Object> pagemap = new HashMap<>();
        pagemap.put("page",page);
        pagemap.put("size",size);
        pagemap.put("content",contents);
        return pagemap;
    }
    @GetMapping("/books/{id}")
    public Object getOne(@PathVariable long id){

        System.out.println("id:"+id);

        return null;
    }
    @PostMapping("/books")
    public Object post(@RequestParam("name") String name,@RequestParam("author") String author,@RequestParam("isbn") String isbn){
        Map<String, Object> book = new HashMap<>();
        book.put("name",name);
        book.put("author",author);
        book.put("isbn",isbn);
        return book;
    }

}
