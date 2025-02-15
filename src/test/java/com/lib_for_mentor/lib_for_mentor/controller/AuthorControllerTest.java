package com.lib_for_mentor.lib_for_mentor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthorControllerTest extends TestConfig{

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Rollback
    void createAuthor() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // Создаем JSON-объект для автора
        ObjectNode author = objectMapper.createObjectNode()
                .put("firstName", "NewAuthorFirstName")
                .put("lastName", "NewAuthorLastName");

        // Создаем массив книг
        ArrayNode booksArray = objectMapper.createArrayNode();

        // Первая книга
        ObjectNode book1 = objectMapper.createObjectNode()
                .put("title", "Первая книга")
                .put("year", 2020)
                .put("genreId", 1)
                .put("publisherId", 1);
        booksArray.add(book1);

        // Вторая книга
        ObjectNode book2 = objectMapper.createObjectNode()
                .put("title", "Вторая книга")
                .put("year", 2022)
                .put("genreId", 1)
                .put("publisherId", 1);
        booksArray.add(book2);

        // Добавляем книги в объект автора
        author.set("books", booksArray);

        // Преобразуем JSON в строку
        String authorJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(author);

        mockMvc.perform(post("/library/author/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("NewAuthorFirstName"))
                .andExpect(jsonPath("$.lastName").value("NewAuthorLastName"))
                .andExpect(jsonPath("$.books").isArray())  // Проверяем, что поле books — это массив
                .andExpect(jsonPath("$.books[0].title").value("Первая книга"))  // Проверяем первую книгу
                .andExpect(jsonPath("$.books[1].title").value("Вторая книга")); // Проверяем вторую книгу
    }

    @Test
    @Rollback
    void updateAuthor() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode author = objectMapper.createObjectNode()
                .put("firstName", "AuthorNewFirstName")
                .put("lastName", "AuthorNewLastName");

        String authorJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(author);

        mockMvc.perform(patch("/library/author/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("AuthorNewFirstName"))
                .andExpect(jsonPath("$.lastName").value("AuthorNewLastName"));
    }

    @Test
    @Rollback
    void assignBook() throws Exception{
        mockMvc.perform(patch("/library/author/2/assignBook/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books[0].id").value("2"));
    }

    @Test
    @Rollback
    void unassignBook() throws Exception{
        mockMvc.perform(patch("/library/author/3/unassignBook/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").isEmpty());
    }

    @Test
    @Rollback
    void deleteAuthor() throws Exception {
        mockMvc.perform(delete("http://localhost:8888/library/author/{id}", 1))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getAuthors() throws Exception {
        mockMvc.perform(get("http://localhost:8888/library/author/")
                        .param("offcet", "0")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    void getAuthorById() throws Exception {
        mockMvc.perform(get("/library/author/{id}", 1)) // корректный путь с подставленным id
                .andExpect(status().isOk()) // ожидаем HTTP 200 OK
                .andExpect(jsonPath("$.id").value(1)) // проверяем, что id = 1
                .andExpect(jsonPath("$.firstName").isNotEmpty()) // firstName не пустой
                .andExpect(jsonPath("$.lastName").isNotEmpty()); // lastName не пустой
    }
}