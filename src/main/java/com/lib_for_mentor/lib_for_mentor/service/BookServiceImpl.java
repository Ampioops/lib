package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.DTO.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import com.lib_for_mentor.lib_for_mentor.model.CreateBookRequest;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.specification.BookSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookSpecification bookSpecification;


    @NotNull
    @Transactional
    public BookResponse create(@NotNull CreateBookRequest request) {
        Book book = buildBoookRequest(request);
        return buildBoookResponce(bookRepository.save(book));
    }

    @NotNull
    @Transactional
    public BookResponse updateInfo(@NotNull Integer id, @NotNull CreateBookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());
        book.setTitle(request.getTitle());
        return buildBoookResponce(bookRepository.save(book));
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
    public List<BookResponse> getAllBooks(BookParamsDTO params) {
        if (params == null) {                                       //Нужно ли допилить так, чтобы не было этого if?????
            return bookRepository.findAll()
                    .stream()
                    .map(this::buildBoookResponce)
                    .collect(Collectors.toList());
        }else {
            return bookRepository.findAll(bookSpecification.build(params))
                    .stream()
                    .map(this::buildBoookResponce)
                    .collect(Collectors.toList());
        }
    }

    @NotNull
    @Transactional(readOnly = true)
    public Optional<BookResponse> findById(@NotNull Integer id) {
        return bookRepository.findById(id)
                .map(this::buildBoookResponce);
    }

    @NotNull
    public BookResponse buildBoookResponce(@NotNull Book book) {
        return new BookResponse()
                .setId(book.getId())
                .setDescription(book.getDescription())
                .setPages(book.getPages())
                .setTitle(book.getTitle())
                .setPublishedYear(book.getPublishedYear());

    }

    @NotNull
    public Book buildBoookRequest(@NotNull CreateBookRequest request) {
        return new Book()
                .setId(request.getId())
                .setDescription(request.getDescription())
                .setPages(request.getPages())
                .setPublishedYear(request.getPublishedYear())
                .setTitle(request.getTitle());

    }
}
