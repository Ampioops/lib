package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.model.dto.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import com.lib_for_mentor.lib_for_mentor.model.BookRequest;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.service.BookService;
import com.lib_for_mentor.lib_for_mentor.specification.BookSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookSpecification bookSpecification;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    //AuthorRepo

    @NotNull
    @Transactional
    public BookResponse create(@NotNull BookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .publishedYear(request.getPublishedYear())
                .description(request.getDescription())
                .pages(request.getPages())
                .author(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found")))
                .build();
        bookRepository.save(book);

        return bookMapper.bookToBookResponse(book);
    }

    @NotNull
    @Transactional
    public BookResponse updateInfo(@NotNull Integer id, @NotNull BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());  //Нужно ли создать класс, где это само реализуется?
        book.setTitle(request.getTitle());
        book.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found")));
        bookRepository.save(book);

        return bookMapper.bookToBookResponse(book);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        bookRepository.delete(book);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<BookResponse> getAllBooks(BookParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page <Book> books = bookRepository.findAll(bookSpecification.build(params), pageRequest);
        return books.map(bookMapper::bookToBookResponse);
    }

    @NotNull
    @Transactional(readOnly = true)
    public BookResponse findById(@NotNull Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        return bookMapper.bookToBookResponse(book);
    }


}
