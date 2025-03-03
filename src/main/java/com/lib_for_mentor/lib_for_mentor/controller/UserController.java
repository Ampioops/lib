package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.client.SubscriptionClient;
import com.lib_for_mentor.lib_for_mentor.client.dto.SubscriptionParam;
import com.lib_for_mentor.lib_for_mentor.client.dto.SubscriptionResponse;
import com.lib_for_mentor.lib_for_mentor.model.param.UserParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.UserRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.UserResponseDTO;
import com.lib_for_mentor.lib_for_mentor.service.BookService;
import com.lib_for_mentor.lib_for_mentor.service.UserService;
import com.lib_for_mentor.lib_for_mentor.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/user")
@RequiredArgsConstructor //Автоматом конструктор создает DI
@Tag(name = "Пользователи",
        description = "Управление данными пользователей",
        externalDocs = @ExternalDocumentation(
                description = "Фейк ссылка на общую документацию",
                url = "https://example.com/docs/user-controller"
        ))
public class UserController {

    private final UserService userService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request) {
        return userService.create(request);
    }

    @PatchMapping(value ="/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO updateUser(@PathVariable("userId") Integer userId, @RequestBody UserRequestDTO request) {
        return userService.updateUserInfo(userId, request);
    }

    @PatchMapping(value ="/{userId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO assignBook(@PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId) {
        return userService.assignBook(userId, bookId);
    }

    @PatchMapping(value ="/{userId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO unassignBook(@PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId) {
        return userService.unassignBook(userId, bookId);
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteAuthor(@PathVariable("userId") Integer userId) {
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

    @GetMapping(value = "/books/{userId}", produces = APPLICATION_JSON_VALUE)
    public Page<BookResponseDTO> getUserBooks(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit,
            @PathVariable("userId") Integer userId) {
        return userService.getUserBooks(userId, offset, limit);
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public UserResponseDTO getUserById(@PathVariable("userId") @NotNull Integer userId) {
        return userService.findById(userId);
    }

    @GetMapping(value = "/subs/{userId}")
    public Page<SubscriptionResponse> getUserSubscriptions(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ){
        return userService.getSubscriptions(userId, offset, limit);
    }
}
