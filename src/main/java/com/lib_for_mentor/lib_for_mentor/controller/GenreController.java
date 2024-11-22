package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.GenreRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.GenreResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.service.impl.AuthorServiceImpl;
import com.lib_for_mentor.lib_for_mentor.service.impl.GenreServiceImpl;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreServiceImpl genreService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public GenreResponseDTO createGenre(@RequestBody GenreRequestDTO request) {
        return genreService.create(request);
    }

    @PatchMapping(value ="/{genreId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public GenreResponseDTO updateGenre(@PathVariable Integer genreId, @RequestBody GenreRequestDTO request) {
        return genreService.updateGenre(genreId, request);
    }

    @PatchMapping(value ="/{genreId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public GenreResponseDTO assignBook(@PathVariable Integer genreId, @PathVariable Integer bookId) {
        return genreService.assignBook(genreId, bookId);
    }

    @PatchMapping(value ="/{genreId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public GenreResponseDTO unassignBook(@PathVariable Integer genreId, @PathVariable Integer bookId) {
        return genreService.unassignBook(genreId, bookId);
    }

    @DeleteMapping(value = "/{genreId}")
    public void deleteAuthor(@PathVariable Integer genreId) {
        genreService.deleteById(genreId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Page<GenreResponseDTO> getAuthors(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ) {
        return genreService.getGenres(offset, limit);
    }

    @GetMapping(value = "/{genreId}", produces = APPLICATION_JSON_VALUE)
    public GenreResponseDTO getAuthorById(@PathVariable @NotNull Integer genreId) {
        return genreService.findById(genreId);
    }

}
