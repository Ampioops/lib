package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface BookService {

    @NotNull
    Book create(@NotNull BookRequestDTO request);

    @NotNull
    Book updateInfo(@NotNull Integer id, @NotNull BookRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<Book> getAllBooks(BookParamsDTO params, Integer offset, Integer limit);

    @NotNull
    Book findById(@NotNull Integer id);
}
