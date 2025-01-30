package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.exception.BadRequestException;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.GenreMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.PublisherMapper;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.GenreResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.PublisherResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.repository.PublisherRepository;
import com.lib_for_mentor.lib_for_mentor.service.impl.AuthorServiceImpl;
import com.lib_for_mentor.lib_for_mentor.service.impl.BookServiceImpl;
import com.lib_for_mentor.lib_for_mentor.specification.BookSpecification;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookSpecification bookSpecification;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private PublisherMapper publisherMapper;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBook_ShouldCreateSuccessfully() {
        Integer authorId = 1;
        Integer genreId = 1;
        Integer publisherId = 1;
        BookRequestDTO request = BookRequestDTO.builder()
                .title("title")
                .publishedYear(2000)
                .description("description")
                .pages(200)
                .authorId(authorId)
                .genreId(genreId)
                .publisherId(publisherId)
                .build();

        Author author = Author.builder()
                .id(authorId)
                .build();

        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .id(authorId)
                .build();

        Genre genre = Genre.builder()
                .id(genreId)
                .build();

        GenreResponseDTO genreResponseDTO = GenreResponseDTO.builder()
                .id(genreId)
                .build();

        Publisher publisher = Publisher.builder()
                .id(publisherId)
                .build();

        PublisherResponseDTO publisherResponseDTO = PublisherResponseDTO.builder()
                .id(publisherId)
                .build();

        Book book = Book.builder()
                .title("title")
                .publishedYear(2000)
                .description("description")
                .pages(200)
                .author(author)
                .genre(genre)
                .publisher(publisher)
                .build();

        BookResponseDTO bookResponseDTO = BookResponseDTO.builder()
                .title("title")
                .publishedYear(2000)
                .description("description")
                .pages(200)
                .author(authorResponseDTO)
                .genre(genreResponseDTO)
                .publisher(publisherResponseDTO)
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));
        when(authorMapper.toAuthorResponse(author)).thenReturn(authorResponseDTO);
        when(genreMapper.toGenreResponse(genre)).thenReturn(genreResponseDTO);
        when(publisherMapper.toPublisherResponse(publisher)).thenReturn(publisherResponseDTO);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponseDTO);

        BookResponseDTO result = bookService.create(request);

        assertEquals(bookResponseDTO, result);
        verify(authorRepository).findById(authorId);
        verify(genreRepository).findById(genreId);
        verify(publisherRepository).findById(publisherId);
        verify(bookRepository).save(book);
        verify(bookMapper).toBookResponse(book);
    }

    @Test
    void updateBook_ShouldUpdateSuccessfully() {
        Integer bookId = 1;
        Integer authorId = 1;
        Integer genreId = 1;
        Integer publisherId = 1;

        BookRequestDTO request = BookRequestDTO.builder()
                .title("title")
                .publishedYear(2000)
                .description("description")
                .pages(200)
                .authorId(authorId)
                .genreId(genreId)
                .publisherId(publisherId)
                .build();

        Author author = Author.builder()
                .id(authorId)
                .build();

        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .id(authorId)
                .build();

        Genre genre = Genre.builder()
                .id(genreId)
                .build();

        GenreResponseDTO genreResponseDTO = GenreResponseDTO.builder()
                .id(genreId)
                .build();

        Publisher publisher = Publisher.builder()
                .id(publisherId)
                .build();

        PublisherResponseDTO publisherResponseDTO = PublisherResponseDTO.builder()
                .id(publisherId)
                .build();

        Book book = Book.builder()
                .id(bookId)
                .title("oldtitle")
                .publishedYear(2001)
                .description("olddescription")
                .pages(250)
                .build();

        BookResponseDTO bookResponseDTO = BookResponseDTO.builder()
                .id(bookId)
                .title("title")
                .publishedYear(2000)
                .description("description")
                .pages(200)
                .author(authorResponseDTO)
                .genre(genreResponseDTO)
                .publisher(publisherResponseDTO)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher));
        when(genreMapper.toGenreResponse(genre)).thenReturn(genreResponseDTO);
        when(publisherMapper.toPublisherResponse(publisher)).thenReturn(publisherResponseDTO);
        when(authorMapper.toAuthorResponse(author)).thenReturn(authorResponseDTO);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponseDTO);

        BookResponseDTO result = bookService.updateInfo(bookId, request);

        assertEquals(bookResponseDTO, result);
        verify(bookRepository).findById(bookId);
        verify(authorRepository).findById(authorId);
        verify(genreRepository).findById(genreId);
        verify(publisherRepository).findById(publisherId);
        verify(bookRepository).save(book);
        verify(bookMapper).toBookResponse(any(Book.class));
    }

    @Test
    void updateBookInvalidBookId_ShouldThrowRuntimeException() {
        Integer bookId = 1;

        BookRequestDTO request = BookRequestDTO.builder()
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> bookService.updateInfo(bookId,request));
    }

    @Test
    void updateBookInvalidAuthorId_ShouldThrowRuntimeException() {
        Integer bookId = 1;
        Integer authorId = 1;

        BookRequestDTO request = BookRequestDTO.builder()
                .build();


        Book book = Book.builder()
                .id(bookId)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> bookService.updateInfo(bookId,request));
    }

    @Test
    void updateBookInvalidGenreId_ShouldThrowRuntimeException() {
        Integer bookId = 1;
        Integer authorId = 1;
        Integer genreId = 1;

        BookRequestDTO request = BookRequestDTO.builder()
                .build();

        Book book = Book.builder()
                .id(bookId)
                .build();

        Author author = Author.builder()
                .id(authorId)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> bookService.updateInfo(bookId,request));
    }

    @Test
    void updateBookInvalidPublisher_ShouldThrowRuntimeException() {
        Integer bookId = 1;
        Integer authorId = 1;
        Integer genreId = 1;
        Integer publisherId = 1;

        BookRequestDTO request = BookRequestDTO.builder()
                .build();

        Book book = Book.builder()
                .id(bookId)
                .build();

        Author author = Author.builder()
                .id(authorId)
                .build();

        Genre genre = Genre.builder()
                .id(genreId)
                .build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> bookService.updateInfo(bookId,request));
    }

}
