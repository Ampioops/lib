package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import org.common.common_utils.response.AuthorResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface AuthorService {
    @NotNull
    AuthorResponseDTO create(@NotNull CreateAuthorRequestDTO request);

    @NotNull
    AuthorResponseDTO updateAuthorInfo(@NotNull Integer id, @NotNull AuthorRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<AuthorResponseDTO> getAuthors(AuthorParamsDTO params, Integer offset, Integer limit);

    @NotNull
    AuthorResponseDTO findById(@NotNull Integer id);

    @NotNull
    AuthorResponseDTO assignBook (@NotNull Integer authorId, @NotNull Integer bookId);

    @NotNull
    AuthorResponseDTO unassignBook (@NotNull Integer authorId, @NotNull Integer bookId);
}
