package com.lib_for_mentor.lib_for_mentor.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class CreateAuthorRequestDTO {
    private String firstName;
    private String lastName;
    private List<BookRequestDTO> books;
}
