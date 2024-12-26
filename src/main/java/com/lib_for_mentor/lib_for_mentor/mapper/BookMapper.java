package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.User;
import com.lib_for_mentor.lib_for_mentor.model.request.BookRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.AuthorRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // Преобразуем List<User> в List<Integer> (список ID пользователей)
    @IterableMapping(qualifiedByName = "userToId")
    List<Integer> usersToIds(List<User> users);

    // Метод для преобразования User в Integer (ID)
    @Named("userToId")
    default Integer userToId(User user) {
        return user != null ? user.getId() : null;
    }

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "genreId", source = "genre.id"),
            @Mapping(target = "publisherId", source = "publisher.id"),
            @Mapping(target = "userIds", source = "users")
    })
    BookResponseDTO toBookResponse(Book book);

    @Mappings({
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "genre", ignore = true),
            @Mapping(target = "publisher", ignore = true),
            @Mapping(target = "users", ignore = true)
    })
    Book bookRequestDTOToBook(BookRequestDTO bookRequestDTO);

}
