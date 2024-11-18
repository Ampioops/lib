package com.lib_for_mentor.lib_for_mentor.model;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AuthorResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Book> books;
}
