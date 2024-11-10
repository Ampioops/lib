package com.lib_for_mentor.lib_for_mentor.model;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data //get, set, toString, equals, hashCode
@Accessors(chain = true)
public class BookResponse {
        private Integer id;
        private String title;
        private int publishedYear;
        private int pages;
        private String description;
}
