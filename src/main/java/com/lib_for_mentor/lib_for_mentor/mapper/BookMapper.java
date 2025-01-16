package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.User;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.UserResponseDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, GenreMapper.class, PublisherMapper.class})
public interface BookMapper {

    @Mappings({
        @Mapping(target = "author.books", ignore = true),
        @Mapping(target = "genre.books", ignore = true),
        @Mapping(target = "publisher.books", ignore = true),
        @Mapping(target = "users.books", ignore = true)
    })
    BookResponseDTO toBookResponse(Book book);

    @Mappings({
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

    @Mapping(target = "books", ignore = true)
    UserResponseDTO toUserResponse(User user);
}
