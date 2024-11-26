package com.lib_for_mentor.lib_for_mentor.model.request;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class CreateAuthorRequestDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Book> books = new ArrayList<>();
}
