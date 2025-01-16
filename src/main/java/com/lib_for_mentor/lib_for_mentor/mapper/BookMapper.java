package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AuthorMapper.class, GenreMapper.class, PublisherMapper.class})
public interface BookMapper {

    @Mappings({
        @Mapping(target = "author.books", ignore = true),
        @Mapping(target = "genre.books", ignore = true),
        @Mapping(target = "publisher.books", ignore = true),
        @Mapping(target = "users.books", ignore = true),
        @Mapping(target = "users", qualifiedByName = "toUserResponseForBookMapper")
    })
    BookResponseDTO toBookResponse(Book book);

    @Mappings({
            @Mapping(target = "author", source = "author"),
            @Mapping(target = "genre", source = "genre"),
            @Mapping(target = "publisher", source = "publisher"),
            @Mapping(target = "users", source = "users", qualifiedByName = "toUserResponseForBookMapper"),

            @Mapping(target = "author.books", ignore = true),
            @Mapping(target = "genre.books", ignore = true),
            @Mapping(target = "publisher.books", ignore = true),
            @Mapping(target = "users.books", ignore = true)
    })
    List<BookResponseDTO> toBookResponses(List<Book> books);

    @Mappings({
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "genre", ignore = true),
            @Mapping(target = "publisher", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    Book bookRequestDTOToBook(BookRequestDTO bookRequestDTO);
}
