package com.lib_for_mentor.lib_for_mentor.Service;

import com.lib_for_mentor.lib_for_mentor.models.CreateBookRequest;
import com.lib_for_mentor.lib_for_mentor.models.BookResponce;
import jakarta.validation.constraints.NotNull;


import java.util.List;


public interface BookService {

    @NotNull
    BookResponce create(@NotNull CreateBookRequest request);

    @NotNull
    BookResponce updateInfo(@NotNull int id, @NotNull CreateBookRequest request);

    @NotNull
    void deleteById(@NotNull int id);

    @NotNull
    List<BookResponce> getAllBooks();

    @NotNull
    List<BookResponce> findAllByTitle(@NotNull String title);
}
