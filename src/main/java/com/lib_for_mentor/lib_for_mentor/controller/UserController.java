package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.model.param.UserParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.UserRequestDTO;
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
        return userService.updateUserInfo(userId, request);
    }

    @PatchMapping(value ="/{userId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO assignBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return userService.assignBook(userId, bookId);
    }

    @PatchMapping(value ="/{userId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO unassignBook(@PathVariable Integer userId, @PathVariable Integer bookId) {
        return userService.unassignBook(userId, bookId);
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteAuthor(@PathVariable Integer userId) {
        userService.deleteById(userId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Page<UserResponseDTO> getUsers(
            @RequestBody(required = false) UserParamsDTO params,
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
