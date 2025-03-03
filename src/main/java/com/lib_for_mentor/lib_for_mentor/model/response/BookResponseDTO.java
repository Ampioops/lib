package com.lib_for_mentor.lib_for_mentor.model.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data //get, set, toString, equals, hashCode
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {
    private Integer id;
    private String title;
    private Integer publishedYear;
    private Integer pages;
    private String description;
    private AuthorResponseDTO author;
    private GenreResponseDTO genre;
    private PublisherResponseDTO publisher;
    private List<UserResponseDTO> users;
}
