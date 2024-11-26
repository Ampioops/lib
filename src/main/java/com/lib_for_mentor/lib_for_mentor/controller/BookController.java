package com.lib_for_mentor.lib_for_mentor.controller;


import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.service.impl.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
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
    private final BookMapper bookMapper;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponseDTO createBook(@RequestBody BookRequestDTO request) {
        Book book = bookService.create(request);
        return bookMapper.bookToBookResponse(book);
    }

    @PatchMapping(value ="/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponseDTO updateBook(@PathVariable Integer bookId, @RequestBody BookRequestDTO request) {
        Book book = bookService.updateInfo(bookId, request);
        return bookMapper.bookToBookResponse(book);
    }

    @DeleteMapping(value = "/{bookId}")
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    @GetMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Page<BookResponseDTO> getBooks(
            @RequestBody(required = false) BookParamsDTO params,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ) {
        Page<Book> books = bookService.getAllBooks(params, offset, limit);
        return books.map(bookMapper::bookToBookResponse);
    }

    @GetMapping(value = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    public BookResponseDTO getBookById(@PathVariable @NotNull Integer bookId) {
        Book book = bookService.findById(bookId);
        return bookMapper.bookToBookResponse(book);
    }

}
