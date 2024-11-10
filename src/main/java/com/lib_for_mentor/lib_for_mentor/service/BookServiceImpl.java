package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.DTO.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
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
        Book book = Book.builder()
                .title(request.getTitle())
                .publishedYear(request.getPublishedYear())
                .description(request.getDescription())
                .pages(request.getPages())
                .build();
        bookRepository.save(book);

        return BookMapper.INSTANCE.bookToBookResponse(book);
    }

    @NotNull
    @Transactional
    public BookResponse updateInfo(@NotNull Integer id, @NotNull CreateBookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());
        book.setTitle(request.getTitle());
        bookRepository.save(book);

        return BookMapper.INSTANCE.bookToBookResponse(book);
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
        if (params == null) {                                       //Нужно ли допилить так, чтобы не было if?????
//            return bookRepository.findAll()
//                    .stream()
//                    .map(this::buildBoookResponse)
//                    .collect(Collectors.toList());
            return BookMapper.INSTANCE.booksToBookResponses(bookRepository.findAll());
        }else {
            return BookMapper.INSTANCE.booksToBookResponses(bookRepository.findAll(bookSpecification.build(params)));
//                    bookRepository.findAll(bookSpecification.build(params))
//                    .stream()
//                    .map(this::buildBoookResponse)
//                    .collect(Collectors.toList());
        }
    }

    @NotNull
    @Transactional(readOnly = true)
    public Optional<BookResponse> findById(@NotNull Integer id) {
        if (bookRepository.findById(id).isPresent()) {
            return BookMapper.INSTANCE.optionalBookToBookResponse(bookRepository.findById(id)); //Не понимаю как тут будет вести себя Optional с Mapstruct
        }else{
            return Optional.empty();
        }
    }


}
