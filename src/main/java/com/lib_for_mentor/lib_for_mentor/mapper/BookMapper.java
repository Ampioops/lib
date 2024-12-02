package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Mapper(componentModel = "spring")
public abstract class BookMapper {

    protected AuthorRepository authorRepository;

    @Mapping(target = "authorId", source = "author.id")
    public abstract BookResponseDTO toBookResponse(Book book);

    @Mapping(target = "author", source = "authorId")
    public abstract Book bookRequestDTOToBook(BookRequestDTO bookRequestDTO);

    protected Author mapAuthor(Integer authorId) {
        if (authorId == null) {
            return null;
        }
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
    }

    @Mapping(target = "author", source = "authorId")
    public abstract Book bookResponseDTOToBook(BookResponseDTO bookResponseDTO);
}
