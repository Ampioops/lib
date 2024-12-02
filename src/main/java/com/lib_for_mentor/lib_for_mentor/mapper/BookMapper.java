package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Mapping(target = "authorId", source = "author.id")
    public abstract BookResponseDTO toBookResponse(Book book);

    @Mapping(target = "author", expression = "java(mapAuthor(bookRequestDTO.getAuthorId(), authorRepository))")
    public abstract Book bookRequestDTOToBook(@Context AuthorRepository authorRepository, BookRequestDTO bookRequestDTO);

    @Mapping(target = "author", expression = "java(mapAuthor(bookResponseDTO.getAuthorId(), authorRepository))")
    public abstract Book bookResponseDTOToBook(@Context AuthorRepository authorRepository, BookResponseDTO bookResponseDTO);

    protected Author mapAuthor(Integer authorId, AuthorRepository authorRepository) {
        if (authorId == null) {
            return null;
        }
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
    }
}
