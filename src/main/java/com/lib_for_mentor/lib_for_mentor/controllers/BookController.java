package com.lib_for_mentor.lib_for_mentor.controllers;


import com.lib_for_mentor.lib_for_mentor.Service.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.models.BookResponce;
import com.lib_for_mentor.lib_for_mentor.models.CreateBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//·	Добавление новой книги. +
//·	Редактирование информации о книге. +
//        ·	Удаление книги. +
//        ·	Просмотр списка всех книг. +
//        ·	Поиск книги по названию. +

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor //Автоматом конструктор создает DI

public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping(value ="/addbook", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponce createBook(@RequestBody CreateBookRequest request) {
        return bookService.create(request);
    }

    @PatchMapping(value ="/updatebook/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponce updateBook(@PathVariable Integer bookId, @RequestBody CreateBookRequest request) {
        return bookService.updateInfo(bookId, request);
    }

    @DeleteMapping(value = "/deletebook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    @GetMapping(value = "/allbooks", produces = APPLICATION_JSON_VALUE)
    public List<BookResponce> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/allbooks/search", produces = APPLICATION_JSON_VALUE)
    public List<BookResponce> getBooksByTitle(@RequestParam String title) {
        return bookService.findAllByTitle(title);
    }

}
