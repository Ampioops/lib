package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.model.param.AuthorParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.AuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.CreateAuthorRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.UserRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.AuthorResponseDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.UserResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface UserService {
    @NotNull
    UserResponseDTO create(@NotNull UserRequestDTO request);

    @NotNull
    UserResponseDTO updateUserInfo(@NotNull Integer id, @NotNull UserRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<UserResponseDTO> getAuthors(UserParamsDTO params, Integer offset, Integer limit);

    @NotNull
    UserResponseDTO findById(@NotNull Integer id);

    @NotNull
    UserResponseDTO assignBook (@NotNull Integer authorId, @NotNull Integer bookId);

    @NotNull
    UserResponseDTO unassignBook (@NotNull Integer authorId, @NotNull Integer bookId);
}
