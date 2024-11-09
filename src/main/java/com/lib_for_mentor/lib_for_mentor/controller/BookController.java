package com.lib_for_mentor.lib_for_mentor.controller;


import com.lib_for_mentor.lib_for_mentor.DTO.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.service.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import com.lib_for_mentor.lib_for_mentor.model.CreateBookRequest;
import com.lib_for_mentor.lib_for_mentor.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/book")
@RequiredArgsConstructor //Автоматом конструктор создает DI

public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponse createBook(@RequestBody CreateBookRequest request) {
        return bookService.create(request);
    }

    @PatchMapping(value ="/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponse updateBook(@PathVariable Integer bookId, @RequestBody CreateBookRequest request) {
        return bookService.updateInfo(bookId, request);
    }

    @DeleteMapping(value = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public List<BookResponse> getBooks(@RequestBody(required = false) BookParamsDTO params) {
        return bookService.getAllBooks(params);
    }

    @GetMapping(value = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    public Optional<BookResponse> getBookById(@PathVariable Integer bookId) {
        return bookService.findById(bookId);
    }

}
