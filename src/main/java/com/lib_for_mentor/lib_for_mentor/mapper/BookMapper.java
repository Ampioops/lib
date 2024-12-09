package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import com.lib_for_mentor.lib_for_mentor.repository.GenreRepository;
import com.lib_for_mentor.lib_for_mentor.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genreId", source = "genre.id"),
            @Mapping(target = "publisherId", source = "publisher.id")
    })
    public abstract BookResponseDTO toBookResponse(Book book);

    @Mapping(target = "author", expression = "java(mapAuthor(bookRequestDTO.getAuthorId(), authorRepository))")
    public abstract Book bookRequestDTOToBook(@Context AuthorRepository authorRepository, BookRequestDTO bookRequestDTO);

    @Mapping(target = "author", expression = "java(mapAuthor(bookResponseDTO.getAuthorId(), authorRepository))")
    public abstract Book bookResponseDTOToBook(@Context AuthorRepository authorRepository, BookResponseDTO bookResponseDTO);

    @Mapping(target = "genre", expression = "java(mapGenre(bookRequestDTO.getGenreId(), genreRepository))")
    public abstract Book bookRequestDTOToBook(@Context GenreRepository genreRepository, BookRequestDTO bookRequestDTO);

    @Mapping(target = "genre", expression = "java(mapGenre(bookResponseDTO.getGenreId(), genreRepository))")
    public abstract Book bookResponseDTOToBook(@Context GenreRepository genreRepository, BookResponseDTO bookResponseDTO);

    @Mapping(target = "publisher", expression = "java(mapPublisher(bookRequestDTO.getPublisherId(), publisherRepository))")
    public abstract Book bookRequestDTOToBook(@Context PublisherRepository publisherRepository, BookRequestDTO bookRequestDTO);

    @Mapping(target = "publisher", expression = "java(mapPublisher(bookResponseDTO.getPublisherId(), publisherRepository))")
    public abstract Book bookResponseDTOToBook(@Context PublisherRepository publisherRepository, BookResponseDTO bookResponseDTO);

    protected Publisher mapPublisher(Integer publisherId, PublisherRepository publisherRepository) {
        if (publisherId == null) {
            return null;
        }
        return publisherRepository.findById(publisherId)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found with id: " + publisherId));
    }

    protected Genre mapGenre(Integer genreId, GenreRepository genreRepository) {
        if (genreId == null) {
            return null;
        }
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found with id: " + genreId));
    }

    protected Author mapAuthor(Integer authorId, AuthorRepository authorRepository) {
        if (authorId == null) {
            return null;
        }
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
    }
}
