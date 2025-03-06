package com.lib_for_mentor.lib_for_mentor.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {
    private String title;
    private Integer publishedYear;
    private Integer pages;
    private String description;
    private Integer authorId;
    private Integer genreId;
    private Integer publisherId;
}
