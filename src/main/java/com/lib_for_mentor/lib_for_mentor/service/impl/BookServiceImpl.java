package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapperImpl;
import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
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
    private final BookMapperImpl bookMapperImpl;

    @NotNull
    @Transactional
    public BookResponseDTO create(@NotNull BookRequestDTO request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .publishedYear(request.getPublishedYear())
                .description(request.getDescription())
                .pages(request.getPages())
                .author(authorRepository.findById(request.getAuthorId()).orElse(null))
                .build();
        bookRepository.save(book);
        return bookMapperImpl.toBookResponse(book);
    }

    @NotNull
    @Transactional
    public BookResponseDTO updateInfo(@NotNull Integer id, @NotNull BookRequestDTO request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());
        book.setTitle(request.getTitle());
        book.setAuthor(authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found")));
        bookRepository.save(book);
        return bookMapperImpl.toBookResponse(book);
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
    public Page<BookResponseDTO> getAllBooks(BookParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return bookRepository.findAll(bookSpecification.build(params), pageRequest).map(bookMapperImpl::toBookResponse);
    }

    @NotNull
    @Transactional(readOnly = true)
    public BookResponseDTO findById(@NotNull Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        return bookMapperImpl.toBookResponse(book);
    }
}
