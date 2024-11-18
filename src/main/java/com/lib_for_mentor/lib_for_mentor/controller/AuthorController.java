package com.lib_for_mentor.lib_for_mentor.controller;


import com.lib_for_mentor.lib_for_mentor.service.impl.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library/author")
@RequiredArgsConstructor //Автоматом конструктор создает DI
public class AuthorController {
    private final AuthorServiceImpl authorService;

}
