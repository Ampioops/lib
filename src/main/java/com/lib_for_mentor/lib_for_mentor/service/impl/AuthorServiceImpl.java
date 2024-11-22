package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.model.AuthorRequest;
import com.lib_for_mentor.lib_for_mentor.model.AuthorResponse;
import com.lib_for_mentor.lib_for_mentor.model.CreateAuthorRequest;
import com.lib_for_mentor.lib_for_mentor.model.dto.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.service.AuthorService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;

    @NotNull
    @Transactional
    public AuthorResponse create(@NotNull CreateAuthorRequest request) {
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        authorRepository.save(author);
        return authorMapper.authorToAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponse updateAuthorInfo(@NotNull Integer id, @NotNull AuthorRequest request) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        authorRepository.save(author);
        return authorMapper.authorToCreateAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
    }

    @NotNull
    public Page<AuthorResponse> getAuthors(AuthorParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page <Author> authors = authorRepository.findAll(pageRequest); //+Specification
        return authors.map(authorMapper::authorToAuthorResponse); //не понимаю как через маппер сделать
    }

    @NotNull
    public AuthorResponse findById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return authorMapper.authorToCreateAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponse assignBook(@NotNull Integer authorId,@NotNull Integer bookId) {

        Optional<Book> book1 = bookRepository.findById(4);
        if (book1.isPresent()) {
            System.out.println("Book found: " + book1.get());
        } else {
            System.out.println("Book not found!");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Book not found"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.getBooks().add(book);
        authorRepository.save(author);

        book.setAuthor(author);
        bookRepository.save(book);

        return authorMapper.authorToAuthorResponse(author);
    }
}
