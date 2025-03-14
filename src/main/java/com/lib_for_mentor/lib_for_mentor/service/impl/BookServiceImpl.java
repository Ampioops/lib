package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.model.event.BookEvent;
import com.lib_for_mentor.lib_for_mentor.model.param.BookParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import org.common.common_utils.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.repository.PublisherRepository;
import com.lib_for_mentor.lib_for_mentor.service.BookService;
import com.lib_for_mentor.lib_for_mentor.specification.BookSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookSpecification bookSpecification;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final KafkaTemplate<String, BookEvent> kafkaTemplate;

    @NotNull
    @Transactional
    public BookResponseDTO create(@NotNull BookRequestDTO request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .publishedYear(request.getPublishedYear())
                .description(request.getDescription())
                .pages(request.getPages())
                .author(authorRepository.findById(request.getAuthorId()).orElse(null))
                .genre(genreRepository.findById(request.getGenreId()).orElse(null))
                .publisher(publisherRepository.findById(request.getPublisherId()).orElse(null))
                .build();
        Integer bookId =  bookRepository.save(book).getId();

        BookEvent event = BookEvent.builder()
                .eventType("CREATED")
                .genreId(book.getGenre() != null ? book.getGenre().getId() : null)
                .authorId(book.getAuthor() != null ? book.getAuthor().getId() : null)
                .bookId(bookId)
                .build();

        kafkaTemplate.send("book-events", event);

        return bookMapper.toBookResponse(book);
    }

    @NotNull
    @Transactional
    public BookResponseDTO updateInfo(@NotNull Integer id, @NotNull BookRequestDTO request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());
        book.setTitle(request.getTitle());
        book.setAuthor(authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found")));
        book.setGenre(genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found")));
        book.setPublisher(publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found")));
        bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        bookRepository.delete(book);

        BookEvent event = BookEvent.builder()
                .eventType("DELETED")
                .genreId(book.getGenre() != null ? book.getGenre().getId() : null)
                .authorId(book.getAuthor() != null ? book.getAuthor().getId() : null)
                .bookId(id)
                .build();

        kafkaTemplate.send("book-events", event);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> getAllBooks(BookParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return bookRepository.findAll(bookSpecification.build(params), pageRequest)
                .map(bookMapper::toBookResponse);
    }

    @NotNull
    @Transactional(readOnly = true)
    public BookResponseDTO findById(@NotNull Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        return bookMapper.toBookResponse(book);
    }
}
