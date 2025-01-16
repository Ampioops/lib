package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.Publisher;
import com.lib_for_mentor.lib_for_mentor.model.response.PublisherResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface PublisherMapper {
    @Mapping(target = "books", source = "books")
    PublisherResponseDTO toPublisherResponse(Publisher publisher);
}
