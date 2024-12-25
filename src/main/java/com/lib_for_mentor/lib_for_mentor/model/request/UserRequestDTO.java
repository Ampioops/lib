package com.lib_for_mentor.lib_for_mentor.model.request;

import com.lib_for_mentor.lib_for_mentor.model.response.BookResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private List<BookRequestDTO> books;
}
