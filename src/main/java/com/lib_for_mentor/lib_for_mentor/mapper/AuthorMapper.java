package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseNoAuthorIdsInBooksDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponseDTO authorToAuthorResponse(Author author);
}
