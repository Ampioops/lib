package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genreId", source = "genre.id"),
            @Mapping(target = "publisherId", source = "publisher.id")
    })
    BookResponseDTO toBookResponse(Book book);

    @Mappings({
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "genre", ignore = true),
            @Mapping(target = "publisher", ignore = true)
    })
    Book bookRequestDTOToBook(BookRequestDTO bookRequestDTO);

    Book bookResponseDTOToBook(BookResponseDTO bookResponseDTO);

}
