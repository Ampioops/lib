package com.lib_for_mentor.lib_for_mentor.controller;


import com.lib_for_mentor.lib_for_mentor.model.dto.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.service.impl.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import com.lib_for_mentor.lib_for_mentor.model.BookRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/book")
@RequiredArgsConstructor //Автоматом конструктор создает DI

public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponse createBook(@RequestBody BookRequest request) {
        return bookService.create(request);
    }

    @PatchMapping(value ="/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponse updateBook(@PathVariable Integer bookId, @RequestBody BookRequest request) {
        return bookService.updateInfo(bookId, request);
    }

    @DeleteMapping(value = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Page<BookResponse> getBooks(
            @RequestBody BookParamsDTO params,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ) {
        return bookService.getAllBooks(params, offset, limit);
    }

    @GetMapping(value = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    public BookResponse getBookById(@PathVariable @NotNull Integer bookId) {
        return bookService.findById(bookId);
    }

}
