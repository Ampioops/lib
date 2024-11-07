package com.lib_for_mentor.lib_for_mentor.Service;

import com.lib_for_mentor.lib_for_mentor.entities.Book;
import com.lib_for_mentor.lib_for_mentor.models.BookResponce;
import com.lib_for_mentor.lib_for_mentor.models.CreateBookRequest;
import com.lib_for_mentor.lib_for_mentor.repositories.BookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @NotNull
    @Transactional
    public BookResponce create(@NotNull CreateBookRequest request) {
        Book book = buildBoookRequest(request);
        return buildBoookResponce(bookRepository.save(book));
    }

    @NotNull
    @Transactional
    public BookResponce updateInfo(@NotNull int id, @NotNull CreateBookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        book.setDescription(request.getDescription());
        book.setPages(request.getPages());
        book.setPublishedYear(request.getPublishedYear());
        book.setTitle(request.getTitle());
        return buildBoookResponce(bookRepository.save(book));
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id = [%s] not found".formatted(id)));
        bookRepository.delete(book);
    }

    @NotNull
    @Transactional(readOnly = true)
    public List<BookResponce> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::buildBoookResponce)
                .collect(Collectors.toList());
    }

    @NotNull
    @Transactional(readOnly = true)
    public List<BookResponce> findAllByTitle(@NotNull String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title); // Запрос с помощью названия

        if (books.isEmpty()) {
            throw new RuntimeException("Book with title [" + title + "] not found");
        }

        return books
                .stream()
                .map(this::buildBoookResponce)
                .collect(Collectors.toList());
    }

    @NotNull
    public BookResponce buildBoookResponce(@NotNull Book book) {
        return new BookResponce()
                .setId(book.getId())
                .setDescription(book.getDescription())
                .setPages(book.getPages())
                .setTitle(book.getTitle())
                .setPublishedYear(book.getPublishedYear());

    }

    @NotNull
    public Book buildBoookRequest(@NotNull CreateBookRequest request) {
        return new Book()
                .setId(request.getId())
                .setDescription(request.getDescription())
                .setPages(request.getPages())
                .setPublishedYear(request.getPublishedYear());

    }
}
