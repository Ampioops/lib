package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.exception.BadRequestException;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.model.request.*;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.repository.PublisherRepository;
import com.lib_for_mentor.lib_for_mentor.service.impl.AuthorServiceImpl;
import com.lib_for_mentor.lib_for_mentor.specification.AuthorSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private AuthorSpecification authorSpecification;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAuthorMissingLastNameShouldThrowBadRequestException() {
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName(null)
                .books(Collections.emptyList())
                .build();

        assertThrows(BadRequestException.class, () -> authorService.create(request));
    }
    @Test
    void createAuthorMissingFirstNameShouldThrowBadRequestException() {
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName(null)
                .lastName("lastName")
                .books(Collections.emptyList())
                .build();

        assertThrows(BadRequestException.class, () -> authorService.create(request));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenGenreIdIsInvalid() {
        // Arrange
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .books(List.of(BookRequestDTO.builder()
                        .build()))
                .build();

        Mockito.when(genreRepository.findById(request.getBooks().getFirst().getGenreId()))
                .thenReturn(Optional.empty()); // Возвращаем пустой результат

        // Act & Assert
        assertThrows(BadRequestException.class, () -> authorService.create(request));
        Mockito.verify(genreRepository, times(1)).findById(request.getBooks().getFirst().getGenreId());
        Mockito.verifyNoInteractions(publisherRepository);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenPublisherIdIsInvalid() {
        // Arrange
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .books(List.of(BookRequestDTO.builder()
                        .genreId(1)
                        .build()))
                .build();

        Mockito.when(genreRepository.findById(request.getBooks().getFirst().getGenreId()))
                .thenReturn(Optional.of(new Genre())); // Возвращаем пустой результат
        Mockito.when(publisherRepository.findById(request.getBooks().getFirst().getPublisherId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> authorService.create(request));
        Mockito.verify(genreRepository, times(1)).findById(request.getBooks().getFirst().getGenreId());
        Mockito.verify(publisherRepository, times(1)).findById(request.getBooks().getFirst().getPublisherId());
        Mockito.verifyNoInteractions(authorRepository);
    }

    @Test
    void createAuthorValidDataShouldReturnAuthorResponse() {
        // Arrange: Создаём моковые данные для запроса
        BookRequestDTO bookRequest = BookRequestDTO.builder()
                .title("title")
                .genreId(3)
                .publisherId(3)
                .build();

        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .books(List.of(bookRequest))
                .build();

        Genre genre = new Genre();
        genre.setId(3);
        genre.setName("Test Genre");

        Publisher publisher = new Publisher();
        publisher.setId(3);
        publisher.setName("Test Publisher");

        Book book = new Book();
        book.setTitle("title");

        Author author = Author.builder()
                .firstName("John")
                .lastName("Doe")
                .books(List.of(book))
                .build();

        AuthorResponseDTO response = AuthorResponseDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        // Настройка поведения моков
        when(genreRepository.findById(3)).thenReturn(Optional.of(genre));
        when(publisherRepository.findById(3)).thenReturn(Optional.of(publisher));
        when(bookMapper.bookRequestDTOToBook(bookRequest)).thenReturn(book);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.toAuthorResponse(any(Author.class))).thenReturn(response);

        // Act: Вызываем тестируемый метод
        AuthorResponseDTO result = authorService.create(request);

        // Assert: Проверяем результат
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());

        // Проверяем, что моки вызвались с правильными параметрами
        verify(genreRepository).findById(3);
        verify(publisherRepository).findById(3);
        verify(authorRepository).save(any(Author.class));
    }

 }

