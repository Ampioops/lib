package com.lib_for_mentor.lib_for_mentor.service;

import com.lib_for_mentor.lib_for_mentor.model.request.GenreRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.PublisherRequestDTO;
import org.common.common_utils.response.GenreResponseDTO;
import org.common.common_utils.response.PublisherResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface PublisherService {
    @NotNull
    PublisherResponseDTO create(@NotNull PublisherRequestDTO request);

    @NotNull
    PublisherResponseDTO updatePublisher(@NotNull Integer id, @NotNull PublisherRequestDTO request);

    @NotNull
    void deleteById(@NotNull Integer id);

    @NotNull
    Page<PublisherResponseDTO> getPublishers(Integer offset, Integer limit);

    @NotNull
    PublisherResponseDTO findById(@NotNull Integer id);

    @NotNull
    PublisherResponseDTO assignBook (@NotNull Integer publisherId, @NotNull Integer bookId);

    @NotNull
    PublisherResponseDTO unassignBook (@NotNull Integer publisherId, @NotNull Integer bookId);
}
