package com.lib_for_mentor.lib_for_mentor.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateBookRequest {
    private Integer id;
    private String title;
    private Integer publishedYear;
    private Integer pages;
    private String description;
}
