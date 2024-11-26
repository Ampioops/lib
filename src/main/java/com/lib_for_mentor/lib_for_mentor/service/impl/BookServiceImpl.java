package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
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
    private final AuthorRepository authorRepository;

    @NotNull
    @Transactional
    public Book create(@NotNull BookRequestDTO request) {
        Book book;
        if (request.getAuthorId() == null) {
            book = Book.builder()
                    .title(request.getTitle())
                    .publishedYear(request.getPublishedYear())
                    .description(request.getDescription())
                    .pages(request.getPages())
                    .build();
            bookRepository.save(book);
        }else{
            book = Book.builder()
                    .title(request.getTitle())
                    .publishedYear(request.getPublishedYear())
                    .description(request.getDescription())
                    .pages(request.getPages())
                    .author(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found")))
                    .build();
            bookRepository.save(book);
        }
        return book;
    }

    @NotNull
    @Transactional
    public Book updateInfo(@NotNull Integer id, @NotNull BookRequestDTO request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());
        book.setTitle(request.getTitle());
        book.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found")));
        bookRepository.save(book);

        return book;
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
    public Page<Book> getAllBooks(BookParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        if(params != null) {
            return bookRepository.findAll(bookSpecification.build(params), pageRequest);
        }else{
            return bookRepository.findAll(pageRequest);
        }
    }

    @NotNull
    @Transactional(readOnly = true)
    public Book findById(@NotNull Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        return book;
    }
}
