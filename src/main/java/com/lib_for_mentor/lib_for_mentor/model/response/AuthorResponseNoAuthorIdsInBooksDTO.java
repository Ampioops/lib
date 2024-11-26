package com.lib_for_mentor.lib_for_mentor.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class AuthorResponseNoAuthorIdsInBooksDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<BookResponseNoAuthorIdDTO> books;
}
