package com.lib_for_mentor.lib_for_mentor.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
public class BookRequestDTO {
    private Integer id;
    private String title;
    private Integer publishedYear;
    private Integer pages;
    private String description;
    private Integer authorId;
}
