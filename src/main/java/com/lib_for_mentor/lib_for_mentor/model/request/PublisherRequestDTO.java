package com.lib_for_mentor.lib_for_mentor.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Builder
public class PublisherRequestDTO {
    private String name;
    private List<BookRequestDTO> books;
}
