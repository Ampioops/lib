package com.lib_for_mentor.lib_for_mentor.controllers;


import com.lib_for_mentor.lib_for_mentor.Service.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.entities.Book;
import com.lib_for_mentor.lib_for_mentor.models.BookResponce;
import com.lib_for_mentor.lib_for_mentor.models.CreateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//·	Добавление новой книги. +
//·	Редактирование информации о книге.
//        ·	Удаление книги.
//        ·	Просмотр списка всех книг. +
//        ·	Поиск книги по названию. +

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor //Автоматом конструктор создает

public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping(value ="/addbook", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponce createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(CreateBookRequest);
    }

    @PatchMapping
    public BookResponce updateBook(@RequestBody Book request) {

    }

    @GetMapping(value = "/allbooks", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<BookResponce> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/allbooks/{title}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<BookResponce> getBooksByTitle(@RequestParam String title) {
        return bookService.findAllByTitle(title);
    }

}
