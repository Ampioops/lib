package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.PublisherMapper;
import com.lib_for_mentor.lib_for_mentor.model.request.PublisherRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.PublisherResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.PublisherRepository;
import com.lib_for_mentor.lib_for_mentor.service.BookService;
import com.lib_for_mentor.lib_for_mentor.service.PublisherService;
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
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final BookMapper bookMapper;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @NotNull
    @Transactional
    public PublisherResponseDTO create(@NotNull PublisherRequestDTO request) {
        List<Book> books = request.getBooks().stream()
                .map(bookMapper::bookRequestDTOToBook)
                .peek(book -> book.setAuthor(null)) // Не изменяет элемент стрима
                .toList();

        Publisher publisher = Publisher.builder()
                .name(request.getName())
                .books(books)
                .build();

        books.forEach(book -> book.setPublisher(publisher));

        publisherRepository.save(publisher);
        return publisherMapper.toPublisherResponse(publisher);
    }

    @NotNull
    @Transactional
    public PublisherResponseDTO updatePublisher(@NotNull Integer id, @NotNull PublisherRequestDTO request) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        return publisherMapper.toPublisherResponse(publisher);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        publisherRepository.delete(publisher);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<PublisherResponseDTO> getPublishers(Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return publisherRepository.findAll(pageRequest)
                .map(publisherMapper::toPublisherResponse);
    }

    @NotNull
    public PublisherResponseDTO findById(@NotNull Integer id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        return publisherMapper.toPublisherResponse(publisher);
    }

    @NotNull
    @Transactional
    public PublisherResponseDTO assignBook(@NotNull Integer publisherId, @NotNull Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        book.setPublisher(publisher);
        bookRepository.save(book);
        publisher.addBook(book);
        return publisherMapper.toPublisherResponse(publisher);
    }

    @NotNull
    @Transactional
    public PublisherResponseDTO unassignBook(@NotNull Integer publisherId, @NotNull Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        book.setPublisher(null);
        bookRepository.save(book);
        publisher.removeBook(book);
        return publisherMapper.toPublisherResponse(publisher);
    }
}
