package com.lib_for_mentor.lib_for_mentor.Controller;


import com.lib_for_mentor.lib_for_mentor.Service.BookService;
import com.lib_for_mentor.lib_for_mentor.Entity.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/library")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/allbooks")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

//    @GetMapping("allbooks")
//    public List<Book> addBook() {
//        return bookService.addBook(new Book());
//    }

    @PostMapping("/addbook")
    public void addBook() {
        bookService.addBook(new Book());
    }

}
