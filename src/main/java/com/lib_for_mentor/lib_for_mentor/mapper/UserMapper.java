package com.lib_for_mentor.lib_for_mentor.mapper;

import com.lib_for_mentor.lib_for_mentor.entity.User;
import com.lib_for_mentor.lib_for_mentor.model.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface UserMapper {

    @Mapping(target = "books", source = "books")
    UserResponseDTO toUserResponse(User user);

    @Named("toUserResponseForBookMapper")
    @Mapping(target = "books", ignore = true)
    UserResponseDTO toUserResponseForBookMapper(User user);
}
