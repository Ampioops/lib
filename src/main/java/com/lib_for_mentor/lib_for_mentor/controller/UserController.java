package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.UserRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.UserResponseDTO;
import com.lib_for_mentor.lib_for_mentor.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/user")
@RequiredArgsConstructor //Автоматом конструктор создает DI
public class UserController {

    private final UserService userService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request) {
        return userService.create(request);
    }

    @PatchMapping(value ="/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO updateUser(@PathVariable Integer userId, @RequestBody UserRequestDTO request) {
        return userService.updateAuthorInfo(authorId, request);
    }

    @PatchMapping(value ="/{userId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO assignBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return userService.assignBook(authorId, bookId);
    }

    @PatchMapping(value ="/{authorId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO unassignBook(@PathVariable Integer authorId, @PathVariable Integer bookId) {
        return userService.unassignBook(authorId, bookId);
    }

    @DeleteMapping(value = "/{authorId}")
    public void deleteAuthor(@PathVariable Integer authorId) {
        userService.deleteById(authorId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Page<UserResponseDTO> getAuthors(
            @RequestBody(required = false) AuthorParamsDTO params,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ) {
        return userService.getUsers(params, offset, limit);
    }

    @GetMapping(value = "/{authorId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO getAuthorById(@PathVariable @NotNull Integer authorId) {
        return userService.findById(authorId);
    }
}
