package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.exception.AuthorNotFoundException;
import com.lib_for_mentor.lib_for_mentor.exception.BadRequestException;
import com.lib_for_mentor.lib_for_mentor.exception.BookNotFoundException;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.*;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
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
    void createAuthorMissingLastName_ShouldThrowBadRequestException() {
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName(null)
                .books(Collections.emptyList())
                .build();

        assertThrows(BadRequestException.class, () -> authorService.create(request));
    }
    @Test
    void createAuthorMissingFirstName_ShouldThrowBadRequestException() {
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName(null)
                .lastName("lastName")
                .books(Collections.emptyList())
                .build();

        assertThrows(BadRequestException.class, () -> authorService.create(request));
    }

    @Test
    void createAuthorWhenGenreNotFound_ShouldThrowBadRequestException() {
        // Arrange
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .books(List.of(BookRequestDTO.builder()
                        .build()))
                .build();

        when(genreRepository.findById(request.getBooks().getFirst().getGenreId()))
                .thenReturn(Optional.empty()); // Возвращаем пустой результат

        // Act & Assert
        assertThrows(BadRequestException.class, () -> authorService.create(request));
        Mockito.verify(genreRepository, times(1)).findById(request.getBooks().getFirst().getGenreId());
        Mockito.verifyNoInteractions(publisherRepository);
    }

    @Test
    void createAuthorWhenPublisherNotFound_ShouldThrowBadRequestException() {
        // Arrange
        CreateAuthorRequestDTO request = CreateAuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .books(List.of(BookRequestDTO.builder()
                        .genreId(1)
                        .build()))
                .build();

        when(genreRepository.findById(request.getBooks().getFirst().getGenreId()))
                .thenReturn(Optional.of(new Genre())); // Возвращаем пустой результат
        when(publisherRepository.findById(request.getBooks().getFirst().getPublisherId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> authorService.create(request));
        Mockito.verify(genreRepository, times(1)).findById(request.getBooks().getFirst().getGenreId());
        Mockito.verify(publisherRepository, times(1)).findById(request.getBooks().getFirst().getPublisherId());
        Mockito.verifyNoInteractions(authorRepository);
    }

    @Test
    void createAuthorValidData_ShouldReturnAuthorResponse() {
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

        Integer genreId = 3;
        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setName("Test Genre");

        Integer publisherId = 3;
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
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
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));
        when(bookMapper.bookRequestDTOToBook(bookRequest)).thenReturn(book);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.toAuthorResponse(any(Author.class))).thenReturn(response);

        // Act: Вызываем тестируемый метод
        AuthorResponseDTO result = authorService.create(request);

        // Assert: Проверяем результат
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(3, result.getBooks().size());

        // Проверяем, что моки вызвались с правильными параметрами
        verify(genreRepository).findById(3);
        verify(publisherRepository).findById(3);
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void updateAuthorValidData_ShouldReturnUpdatedAuthorResponse() {
        // Arrange
        AuthorRequestDTO request = AuthorRequestDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
        Integer authorId = 1;
        Author author = Author.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .books(Collections.emptyList())
                .build();
        AuthorResponseDTO expected = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("firstName")
                .lastName("lastName")
                .books(Collections.emptyList())
                .build();

        // Настройка поведения моков
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorMapper.toAuthorResponse(any(Author.class))).thenReturn(expected);

        // Act & Assert
        AuthorResponseDTO result = authorService.updateAuthorInfo(authorId, request);
        assertEquals(expected, result);

        verify(authorRepository, times(1)).save(any(Author.class));
        verify(authorRepository).findById(authorId);
        verify(authorRepository).save(any(Author.class));
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void deleteAuthorInvalidData_ShouldThrowAuthorNotFoundException() {
        Integer authorId = 1;
        Author author = Author.builder()
                .id(authorId)
                .build();

        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteById(author.getId()));
    }

    @Test
    void deleteAuthorValidData_ShouldDeleteAuthorSuccessfully() {
        Integer authorId = 1;
        Author author = Author.builder()
                .id(authorId)
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        authorService.deleteById(authorId);

        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).delete(author);
        verifyNoInteractions(authorRepository);
    }

    @Test
    void getAuthorsValidData_ShouldReturnPaginatedAuthorResponse() {
        AuthorParamsDTO params = new AuthorParamsDTO(); // Добавьте необходимые параметры
        PageRequest pageRequest = PageRequest.of(0, 5);

        Author author1 = Author.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        Author author2 = Author.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Smith")
                .build();

        AuthorResponseDTO expectedAuthor1 = AuthorResponseDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        AuthorResponseDTO expectedAuthor2 = AuthorResponseDTO.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Smith")
                .build();
        Page<Author> authors = new PageImpl<>(List.of(author1, author2), pageRequest, 2);
        Page<AuthorResponseDTO> expectedAuthors = new PageImpl<>(List.of(expectedAuthor1, expectedAuthor2), pageRequest, 2);
        Specification<Author> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();


        when(authorSpecification.build(params)).thenReturn(spec);
        when(authorRepository.findAll(eq(spec), eq(pageRequest))).thenReturn(authors);
        when(authorMapper.toAuthorResponse(author1)).thenReturn(expectedAuthor1);
        when(authorMapper.toAuthorResponse(author2)).thenReturn(expectedAuthor2);

        Page<AuthorResponseDTO> result = authorService.getAuthors(params,0,5);

        assertEquals(expectedAuthors, result);
        verify(authorSpecification, times(1)).build(params);
        verify(authorRepository, times(1)).findAll(eq(spec), eq(pageRequest));
        verify(authorMapper, times(1)).toAuthorResponse(author1);
        verify(authorMapper, times(1)).toAuthorResponse(author2);
        verifyNoMoreInteractions(authorRepository, authorMapper);
    }

    @Test
    void findAuthorByIdValidData_ShouldReturnAuthorResponse() {
        Integer authorId = 1;
        Author author = Author.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .build();

        AuthorResponseDTO expectedAuthor = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorMapper.toAuthorResponse(author)).thenReturn(expectedAuthor);

        AuthorResponseDTO result = authorService.findById(authorId);

        assertEquals(expectedAuthor, result);
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toAuthorResponse(author);
        verifyNoMoreInteractions(authorRepository, authorMapper);
    }

    @Test
    void findAuthorByIdInvalidData_ShouldThrowAuthorNotFoundException() {
        Integer invalidAuthorId = 2;
        assertThrows(AuthorNotFoundException.class, () -> authorService.findById(invalidAuthorId));
    }

    @Test
    void assignBookInvalidBookId_ShouldThrowBookNotFoundException() {
        Integer bookId = 1;
        Integer authorId = 2;
        assertThrows(BookNotFoundException.class, () -> authorService.assignBook(authorId, bookId));
    }

    @Test
    void assignBookInvalidAuthorId_ShouldThrowAuthorNotFoundException() {
        Integer bookId = 1;
        Book book = Book.builder()
                .id(bookId)
                .build();
        Integer authorId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertThrows(AuthorNotFoundException.class, () -> authorService.assignBook(authorId, bookId));
    }

    @Test
    void assignBookValidData_ShouldAssignBookSuccessfully() {
        Integer bookId = 1;
        Book book = Book.builder()
                .id(bookId)
                .build();

        BookResponseDTO expectedBook = BookResponseDTO.builder()
                .id(bookId)
                .build();

        Integer authorId = 1;
        Author author = Author.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .books(new ArrayList<>())
                .build();

        AuthorResponseDTO expectedAuthor = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .books(List.of(expectedBook))
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookMapper.toBookResponse(book)).thenReturn(expectedBook);
        when(authorMapper.toAuthorResponse(author)).thenReturn(expectedAuthor);

        AuthorResponseDTO result = authorService.assignBook(authorId, bookId);

        assertEquals(expectedAuthor, result);
        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void unassignBookInvalidBookId_ShouldThrowBookNotFoundException() {
        Integer bookId = 1;
        Integer authorId = 2;
        assertThrows(BookNotFoundException.class, () -> authorService.assignBook(authorId, bookId));
    }

    @Test
    void unassignBookInvalidAuthorId_ShouldThrowAuthorNotFoundException() {
        Integer bookId = 1;
        Book book = Book.builder()
                .id(bookId)
                .build();
        Integer authorId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        assertThrows(AuthorNotFoundException.class, () -> authorService.assignBook(authorId, bookId));
    }

    @Test
    void unassignBookValidData_ShouldAssignBookSuccessfully() {
        Integer bookId = 1;
        Book book = Book.builder()
                .id(bookId)
                .build();

        Integer authorId = 1;
        Author author = Author.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .build();

        book.setAuthor(author);
        author.setBooks(new ArrayList<>(List.of(book)));

        BookResponseDTO expectedBook = BookResponseDTO.builder()
                .id(bookId)
                .author(null)
                .build();

        AuthorResponseDTO expectedAuthor = AuthorResponseDTO.builder()
                .id(authorId)
                .firstName("John")
                .lastName("Doe")
                .books(Collections.emptyList())
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(bookMapper.toBookResponse(book)).thenReturn(expectedBook);
        when(authorMapper.toAuthorResponse(author)).thenReturn(expectedAuthor);

        AuthorResponseDTO result = authorService.assignBook(authorId, bookId);

        assertEquals(expectedAuthor, result);
        verify(bookRepository, times(1)).findById(bookId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

}

