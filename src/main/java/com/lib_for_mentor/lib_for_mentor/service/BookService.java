package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.model.dto.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.CreateBookRequest;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;


import java.util.List;

public interface BookService {

    @NotNull
    BookResponse create(@NotNull CreateBookRequest request);

    @NotNull
    BookResponse updateInfo(@NotNull Integer id, @NotNull CreateBookRequest request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<BookResponse> getAllBooks(BookParamsDTO params, Integer offset, Integer limit);

    @NotNull
    BookResponse findById(@NotNull Integer id);
}
