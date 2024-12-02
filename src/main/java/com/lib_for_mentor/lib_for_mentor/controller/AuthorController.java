package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.service.impl.AuthorServiceImpl;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/author")
@RequiredArgsConstructor //Автоматом конструктор создает DI
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDTO createAuthor(@RequestBody CreateAuthorRequestDTO request) {
        return authorService.create(request);
    }

    @PatchMapping(value ="/{authorId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDTO updateAuthor(@PathVariable Integer authorId, @RequestBody AuthorRequestDTO request) {
        return authorService.updateAuthorInfo(authorId, request);
    }

    @PatchMapping(value ="/{authorId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDTO assignBook(@PathVariable Integer authorId, @PathVariable Integer bookId) {
        return authorService.assignBook(authorId, bookId);
    }

    @PatchMapping(value ="/{authorId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDTO unassignBook(@PathVariable Integer authorId, @PathVariable Integer bookId) {
        return authorService.unassignBook(authorId, bookId);
    }

    @DeleteMapping(value = "/{authorId}")
    public void deleteAuthor(@PathVariable Integer authorId) {
        authorService.deleteById(authorId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Page<AuthorResponseDTO> getAuthors(
            @RequestBody(required = false) AuthorParamsDTO params,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ) {
        return authorService.getAuthors(params, offset, limit);
    }

    @GetMapping(value = "/{authorId}", produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDTO getAuthorById(@PathVariable @NotNull Integer authorId) {
        return authorService.findById(authorId);
    }
}
