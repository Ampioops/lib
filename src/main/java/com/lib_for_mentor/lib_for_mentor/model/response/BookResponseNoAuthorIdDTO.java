package com.lib_for_mentor.lib_for_mentor.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data //get, set, toString, equals, hashCode
@Accessors(chain = true)
@Builder
public class BookResponseNoAuthorIdDTO {
    private Integer id;
    private String title;
    private Integer publishedYear;
    private Integer pages;
    private String description;
}
