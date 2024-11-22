package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.model.request.GenreRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.GenreResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface GenreService {
    @NotNull
    GenreResponseDTO create(@NotNull GenreRequestDTO request);

    @NotNull
    GenreResponseDTO updateGenre(@NotNull Integer id, @NotNull GenreRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<GenreResponseDTO> getGenres(Integer offset, Integer limit);

    @NotNull
    GenreResponseDTO findById(@NotNull Integer id);

    @NotNull
    GenreResponseDTO assignBook (@NotNull Integer genreId, @NotNull Integer bookId);

    @NotNull
    GenreResponseDTO unassignBook (@NotNull Integer genreId, @NotNull Integer bookId);
}
