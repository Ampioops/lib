package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapperImpl;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
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

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookRepository bookRepository;
    private final BookServiceImpl bookServiceImpl;
    private final AuthorSpecification authorSpecification;
    private final BookMapperImpl bookMapperImpl;

    @NotNull
    @Transactional
    public AuthorResponseDTO create(@NotNull CreateAuthorRequestDTO request) {
        List<Book> books = request.getBooks().stream()
                .map(bookMapperImpl::bookRequestDTOToBook)
                .toList();
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .books(books)
                .build();
        authorRepository.save(author);
        return authorMapper.authorToAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponseDTO updateAuthorInfo(@NotNull Integer id, @NotNull AuthorRequestDTO request) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        authorRepository.save(author);
        return authorMapper.authorToAuthorResponse(author);
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
    public Page<AuthorResponseDTO> getAuthors(AuthorParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return (authorRepository.findAll(authorSpecification.build(params), pageRequest)).map(authorMapper::authorToAuthorResponse);
    }

    @NotNull
    public AuthorResponseDTO findById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return authorMapper.authorToAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponseDTO assignBook(@NotNull Integer authorId,@NotNull Integer bookId) {
        Book book = bookServiceImpl.findById(bookId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        book.setAuthor(author);
        bookRepository.save(book);
        return authorMapper.authorToAuthorResponse(author);
    }
}
