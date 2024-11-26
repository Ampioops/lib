package com.lib_for_mentor.lib_for_mentor.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
public class AuthorRequestDTO {
    private Integer id;
    private String firstName;
    private String lastName;
}
