package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import org.common.common_utils.response.BookResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface BookService {


    @NotNull
    BookResponseDTO create(@NotNull BookRequestDTO request);

    @NotNull
    BookResponseDTO updateInfo(@NotNull Integer id, @NotNull BookRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<BookResponseDTO> getAllBooks(BookParamsDTO params, Integer offset, Integer limit);

    @NotNull
    BookResponseDTO findById(@NotNull Integer id);
}
