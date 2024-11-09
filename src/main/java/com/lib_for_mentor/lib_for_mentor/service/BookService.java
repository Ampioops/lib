package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.DTO.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.CreateBookRequest;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import jakarta.validation.constraints.NotNull;


import java.util.List;
import java.util.Optional;


public interface BookService {

    @NotNull
    BookResponse create(@NotNull CreateBookRequest request);

    @NotNull
    BookResponse updateInfo(@NotNull Integer id, @NotNull CreateBookRequest request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    List<BookResponse> getAllBooks(BookParamsDTO params);

    @NotNull
    Optional<BookResponse> findById(@NotNull Integer id);
}
