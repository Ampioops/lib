package com.lib_for_mentor.lib_for_mentor.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
            .withUsername("test_user")
            .withPassword("test_password")
            .withDatabaseName("test_db");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.liquibase.enabled", () -> true);
        registry.add("spring.liquibase.change-log", () -> "db/changelog/main-changelog.xml");
        registry.add("spring.liquibase.url", postgres::getJdbcUrl);
        registry.add("spring.liquibase.user", postgres::getUsername);
        registry.add("spring.liquibase.password", postgres::getPassword);
        registry.add("spring.liquibase.drop-first", () -> true);
    }

    @Test
    void createAuthor() throws Exception {
        String authorJson = """
                {
                    "firstName": "NewAuthorFirstName",
                    "lastName": "NewAuthorLastName",
                    "books": [
                        {
                            "title": "Первая книга",
                            "year": 2020,
                            "genreId": 1,
                            "publisherId": 1
                        },
                        {
                            "title": "Вторая книга",
                            "year": 2022,
                            "genreId": 1,
                            "publisherId": 1
                        }
                    ]
                }
                """;
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
    void updateAuthor() throws Exception {
        String authorJson = """
                {
                    "firstName": "AuthorNewFirstName",
                    "lastName": "AuthorNewLastName"
                }
                """;
        mockMvc.perform(patch("/library/author/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("AuthorNewFirstName"))
                .andExpect(jsonPath("$.lastName").value("AuthorNewLastName"));
    }

    @Test
    void assignBook() throws Exception{
        mockMvc.perform(patch("/library/author/1/assignBook/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books[1].title").value("3BookTitle"));
    }

    @Test
    void unassignBook() throws Exception{
        mockMvc.perform(patch("/library/author/1/unassignBook/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").isEmpty());
    }

    @Test
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