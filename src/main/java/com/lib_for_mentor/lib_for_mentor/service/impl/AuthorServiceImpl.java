package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.service.AuthorService;
import com.lib_for_mentor.lib_for_mentor.specification.AuthorSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;
    private final BookServiceImpl bookServiceImpl;
    private final AuthorSpecification authorSpecification;

    @NotNull
    @Transactional
    public Author create(@NotNull CreateAuthorRequestDTO request) {
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        authorRepository.save(author);
        return author;
    }

    @NotNull
    @Transactional
    public Author updateAuthorInfo(@NotNull Integer id, @NotNull AuthorRequestDTO request) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        authorRepository.save(author);
        return author;
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<Author> getAuthors(AuthorParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        if(params != null) {
            return authorRepository.findAll(authorSpecification.build(params), pageRequest); //+Specification
        }else {
            return authorRepository.findAll(pageRequest);
        }
    }

    @NotNull
    public Author findById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return author;
    }

    //StackOverFlowExc!!!!
    // мб создать свой save(единственный Transactional) в этом сервисе и через него

    @NotNull
    @Transactional
    public Author assignBook(@NotNull Integer authorId,@NotNull Integer bookId) {
        Book book = bookServiceImpl.findById(bookId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.getBooks().add(book);
        authorRepository.save(author);
        book.setAuthor(author);
        bookRepository.save(book);
        return author;
    }
}
