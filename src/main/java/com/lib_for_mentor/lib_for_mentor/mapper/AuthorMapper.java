package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Author;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface AuthorMapper {
    @Mappings({
            @Mapping(target = "books", source = "books")
    })
    AuthorResponseDTO toAuthorResponse(Author author);
}
