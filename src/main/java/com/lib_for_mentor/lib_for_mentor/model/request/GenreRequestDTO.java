package com.lib_for_mentor.lib_for_mentor.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
public class GenreRequestDTO {
    private String name;
}
