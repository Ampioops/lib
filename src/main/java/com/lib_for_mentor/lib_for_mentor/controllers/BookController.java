package com.lib_for_mentor.lib_for_mentor.controllers;


import com.lib_for_mentor.lib_for_mentor.Service.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.entities.Book;
import com.lib_for_mentor.lib_for_mentor.models.BookResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//·	Добавление новой книги.
//·	Редактирование информации о книге.
//        ·	Удаление книги.
//        ·	Просмотр списка всех книг.
//        ·	Поиск книги по названию.

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor //Автоматом конструктор создает

public class BookController {

    private final BookServiceImpl bookService;

    @GetMapping(value = "/allbooks", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<BookResponce> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/allbooks/{bookId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<BookResponce> getBooks(@RequestParam String title) {
        return bookService.findAllByTitle(title);
    }


    @PostMapping("/addbook")
    public Book addBook(@RequestBody Book request) {
        return bookService.addBook(request);
    }

}
