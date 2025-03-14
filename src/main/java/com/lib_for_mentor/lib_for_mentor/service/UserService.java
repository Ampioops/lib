package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.client.dto.SubscriptionResponse;
import com.lib_for_mentor.lib_for_mentor.model.param.UserParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.UserRequestDTO;
import org.common.common_utils.response.BookResponseDTO;
import org.common.common_utils.response.UserResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    @NotNull
    Page<BookResponseDTO> getUserBooks(Integer userId, Integer offset, Integer limit);

    @NotNull
    UserResponseDTO create(@NotNull UserRequestDTO request);

    @NotNull
    UserResponseDTO updateUserInfo(@NotNull Integer id, @NotNull UserRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<UserResponseDTO> getUsers(UserParamsDTO params, Integer offset, Integer limit);

    @NotNull
    UserResponseDTO findById(@NotNull Integer id);

    @NotNull
    UserResponseDTO assignBook (@NotNull Integer authorId, @NotNull Integer bookId);

    @NotNull
    UserResponseDTO unassignBook (@NotNull Integer authorId, @NotNull Integer bookId);

    @NotNull
    Page<SubscriptionResponse> getSubscriptions(@NotNull Integer userId, Integer offset, Integer limit);
}
