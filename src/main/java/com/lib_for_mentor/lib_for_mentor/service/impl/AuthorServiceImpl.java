package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.exception.AuthorNotFoundException;
import com.lib_for_mentor.lib_for_mentor.exception.BadRequestException;
import com.lib_for_mentor.lib_for_mentor.exception.BookNotFoundException;
import com.lib_for_mentor.lib_for_mentor.mapper.AuthorMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.repository.PublisherRepository;
import com.lib_for_mentor.lib_for_mentor.service.AuthorService;
import com.lib_for_mentor.lib_for_mentor.service.BookService;
import com.lib_for_mentor.lib_for_mentor.specification.AuthorSpecification;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorServiceImpl implements AuthorService {

    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final AuthorSpecification authorSpecification;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @NotNull
    @Transactional
    public AuthorResponseDTO create(@NotNull CreateAuthorRequestDTO request) {

        if(request.getLastName() == null || request.getFirstName() == null) {
            throw new BadRequestException("Invalid Author data");
        }

        List<Book> books = request.getBooks().stream()
                .map(bookRequestDTO -> {
                    // Загружаем жанр и издательство из базы данных
                    Genre genre = genreRepository.findById(bookRequestDTO.getGenreId())
                            .orElseThrow(() -> new BadRequestException("Invalid Genre ID: " + bookRequestDTO.getGenreId()));

                    Publisher publisher = publisherRepository.findById(bookRequestDTO.getPublisherId())
                            .orElseThrow(() -> new BadRequestException("Invalid Publisher ID: " + bookRequestDTO.getPublisherId()));

                    // Создаём сущность книги
                    Book book = bookMapper.bookRequestDTOToBook(bookRequestDTO);

                    // Устанавливаем жанр и издательство
                    book.setGenre(genre);
                    book.setPublisher(publisher);

                    return book;
                })
                .toList();

        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .books(books)
                .build();

        // Установка связи "книга -> автор"
        books.forEach(book -> book.setAuthor(author));

        authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponseDTO updateAuthorInfo(@NotNull Integer id, @NotNull AuthorRequestDTO request) {
        if(request.getLastName() == null || request.getFirstName() == null) {
            throw new BadRequestException("Invalid data for Author new info");
        }

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with "+ id +" not found"));

        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        authorRepository.save(author);
        return authorMapper.toAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with "+ id +" not found"));
        authorRepository.delete(author);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<AuthorResponseDTO> getAuthors(AuthorParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return authorRepository.findAll(authorSpecification.build(params), pageRequest)
                .map(authorMapper::toAuthorResponse);
    }

    @NotNull
    public AuthorResponseDTO findById(@NotNull Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with "+ id +" not found"));
        return authorMapper.toAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponseDTO assignBook(@NotNull Integer authorId, @NotNull Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with "+ bookId +" not found"));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with "+ authorId +" not found"));
        book.setAuthor(author);
        bookRepository.save(book);
        author.addBook(book);
        return authorMapper.toAuthorResponse(author);
    }

    @NotNull
    @Transactional
    public AuthorResponseDTO unassignBook(@NotNull Integer authorId, @NotNull Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with "+ bookId +" not found"));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with "+ authorId +" not found"));
        book.setAuthor(null);
        bookRepository.save(book);
        author.removeBook(book);
        return authorMapper.toAuthorResponse(author);
    }
}
