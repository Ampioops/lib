package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface AuthorService {
    @NotNull
    Author create(@NotNull CreateAuthorRequestDTO request);

    @NotNull
    Author updateAuthorInfo(@NotNull Integer id, @NotNull AuthorRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<Author> getAuthors(AuthorParamsDTO params, Integer offset, Integer limit);

    @NotNull
    Author findById(@NotNull Integer id);

    @NotNull
    Author assignBook (@NotNull Integer authorId, @NotNull Integer bookId); //---Остановка тут
}
