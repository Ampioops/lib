package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.model.AuthorRequest;
import com.lib_for_mentor.lib_for_mentor.model.AuthorResponse;
import com.lib_for_mentor.lib_for_mentor.model.dto.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.service.AuthorService;
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

    @NotNull
    @Transactional
    public AuthorResponse create(@NotNull AuthorRequest request) {
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
    public Page<AuthorResponse> getAllAuthors(AuthorParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page <Author> authors = authorRepository.findAll(pageRequest); //+Specification
        return authorMapper.authorsToAuthorResponsesPage(authors);
    }

    @NotNull
    public AuthorResponse findById(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return authorMapper.authorToAuthorResponse(author);
    }
}
