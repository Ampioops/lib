package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.exception.AuthorNotFoundException;
import com.lib_for_mentor.lib_for_mentor.exception.BookNotFoundException;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Авторы", description = "Управление данными авторов")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Создание автора",
            description = "Позволяет создать автора"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автор создан"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    public AuthorResponseDTO createAuthor(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные нового автора", required = true) CreateAuthorRequestDTO request) {
        return authorService.create(request);
    }


    @PatchMapping(value ="/{authorId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Обновление данных автора"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автор обновлен"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Автор не найден")
    })
    public AuthorResponseDTO updateAuthor(@PathVariable @Parameter(description = "Идентификатор автора") Integer authorId, @RequestBody AuthorRequestDTO request) {
        return authorService.updateAuthorInfo(authorId, request);
    }


    @PatchMapping(value ="/{authorId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Присвоение книги автору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Книга присвоена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Автор не найден",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "Книга не найдена",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookNotFoundException.class)))
    })
    public AuthorResponseDTO assignBook(@PathVariable Integer authorId, @PathVariable Integer bookId) {
        return authorService.assignBook(authorId, bookId);
    }

    @PatchMapping(value ="/{authorId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Убрать книгу автора"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Книга убрана"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "404", description = "Автор не найден",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorNotFoundException.class))),
            @ApiResponse(responseCode = "404", description = "Книга не найдена",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BookNotFoundException.class)))
    })
    public AuthorResponseDTO unassignBook(@PathVariable Integer authorId, @PathVariable Integer bookId) {
        return authorService.unassignBook(authorId, bookId);
    }


    @DeleteMapping(value = "/{authorId}")
    @Operation(
            summary = "Удаление автора"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автор удален"),
            @ApiResponse(responseCode = "404", description = "Автор не найден",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorNotFoundException.class)))
    })
    public void deleteAuthor(@PathVariable Integer authorId) {
        authorService.deleteById(authorId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение списка авторов",
            description = "Позволяет получить список авторов в соответствии с параметрами"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список предоставлен"),
    })
    public Page<AuthorResponseDTO> getAuthors(
            @RequestBody(required = false) AuthorParamsDTO params,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) @Parameter(description = "Пропуск указанного количества строк") Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) @Parameter(description = "Предел общего количества строк") Integer limit
    ) {
        return authorService.getAuthors(params, offset, limit);
    }

    @GetMapping(value = "/{authorId}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получить автора по идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автор удален"),
            @ApiResponse(responseCode = "404", description = "Автор не найден",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthorNotFoundException.class)))
    })
    public AuthorResponseDTO getAuthorById(@PathVariable @NotNull Integer authorId) {
        return authorService.findById(authorId);
    }
}
