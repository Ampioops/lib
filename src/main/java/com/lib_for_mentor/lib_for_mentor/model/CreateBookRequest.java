package com.lib_for_mentor.lib_for_mentor.model;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    private Integer authorId;
    private Integer genreId;
    private Integer publisherId;
}
