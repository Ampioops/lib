package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Genre;
import org.common.common_utils.response.GenreResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface GenreMapper {
    @Mapping(target = "books", source = "books")
    GenreResponseDTO toGenreResponse(Genre genre);
}
