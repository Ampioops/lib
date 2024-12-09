package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.GenreMapper;
import com.lib_for_mentor.lib_for_mentor.model.request.GenreRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.GenreResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.service.GenreService;
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
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;
    private final BookServiceImpl bookServiceImpl;
    private final BookRepository bookRepository;
    private final GenreMapper genreMapper;

    @NotNull
    @Transactional
    public GenreResponseDTO create(@NotNull GenreRequestDTO request) {
        List<Book> books = request.getBooks().stream()
                .map(bookRequestDTO -> bookMapper.bookRequestDTOToBook(genreRepository, bookRequestDTO))
                .toList();
        Genre genre = Genre.builder()
                .name(request.getName())
                .books(books)
                .build();
        genreRepository.save(genre);
        return genreMapper.toGenreResponse(genre);
    }

    @NotNull
    @Transactional
    public GenreResponseDTO updateGenre(@NotNull Integer id, @NotNull GenreRequestDTO request) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("genre not found"));
        return genreMapper.toGenreResponse(genre);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("genre not found"));
        genreRepository.delete(genre);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<GenreResponseDTO> getGenres(Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return genreRepository.findAll(pageRequest).map(genreMapper::toGenreResponse);
    }

    @NotNull
    public GenreResponseDTO findById(@NotNull Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return genreMapper.toGenreResponse(genre);
    }

    @NotNull
    @Transactional
    public GenreResponseDTO assignBook(@NotNull Integer genreId, @NotNull Integer bookId) {
        Book book = bookMapper.bookResponseDTOToBook(genreRepository, bookServiceImpl.findById(bookId));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("genre not found"));
        book.setGenre(genre);
        bookRepository.save(book);
        return genreMapper.toGenreResponse(genre);
    }

    @NotNull
    @Transactional
    public GenreResponseDTO unassignBook(@NotNull Integer genreId, @NotNull Integer bookId) {
        Book book = bookMapper.bookResponseDTOToBook(genreRepository, bookServiceImpl.findById(bookId));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("genre not found"));
        book.setGenre(null);
        bookRepository.save(book);
        return genreMapper.toGenreResponse(genre);
    }
}
