package com.lib_for_mentor.lib_for_mentor.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data //get, set, toString, equals, hashCode
@Accessors(chain = true)
public class BookResponce {
        private Integer id;
        private String title;
        private int publishedYear;
        private int pages;
        private String description;
}
