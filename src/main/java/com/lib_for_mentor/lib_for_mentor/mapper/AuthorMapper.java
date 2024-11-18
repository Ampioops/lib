package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.model.AuthorResponse;
import com.lib_for_mentor.lib_for_mentor.model.BookResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse authorToAuthorResponse(Author author);
    List<AuthorResponse> authorsToAuthorResponses(List<Author> authors);
    Page<AuthorResponse> authorsToAuthorResponsesPage(Page<Author> authors);
}
