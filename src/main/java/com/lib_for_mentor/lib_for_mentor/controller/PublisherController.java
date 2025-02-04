package com.lib_for_mentor.lib_for_mentor.controller;

import com.lib_for_mentor.lib_for_mentor.model.request.PublisherRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.PublisherResponseDTO;
import com.lib_for_mentor.lib_for_mentor.service.PublisherService;
import com.lib_for_mentor.lib_for_mentor.service.impl.PublisherServiceImpl;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/library/publisher")
@RequiredArgsConstructor
@Tag(name = "Издательства",
        description = "Управление данными издательств",
        externalDocs = @ExternalDocumentation(
        description = "Фейк ссылка на общую документацию",
        url = "https://example.com/docs/user-controller"
))
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping(value ="/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public PublisherResponseDTO createGenre(@RequestBody PublisherRequestDTO request) {
        return publisherService.create(request);
    }

    @PatchMapping(value ="/{publisherId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public PublisherResponseDTO updateGenre(@PathVariable Integer publisherId, @RequestBody PublisherRequestDTO request) {
        return publisherService.updatePublisher(publisherId, request);
    }

    @PatchMapping(value ="/{publisherId}/assignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public PublisherResponseDTO assignBook(@PathVariable Integer publisherId, @PathVariable Integer bookId) {
        return publisherService.assignBook(publisherId, bookId);
    }

    @PatchMapping(value ="/{publisherId}/unassignBook/{bookId}", produces = APPLICATION_JSON_VALUE)
    public PublisherResponseDTO unassignBook(@PathVariable Integer publisherId, @PathVariable Integer bookId) {
        return publisherService.unassignBook(publisherId, bookId);
    }

    @DeleteMapping(value = "/{genreId}")
    public void deleteGenre(@PathVariable Integer genreId) {
        publisherService.deleteById(genreId);
    }

    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public Page<PublisherResponseDTO> getPublishers(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset, //Пагинация
            @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit
    ) {
        return publisherService.getPublishers(offset, limit);
    }

    @GetMapping(value = "/{genreId}", produces = APPLICATION_JSON_VALUE)
    public PublisherResponseDTO getPublisherById(@PathVariable @NotNull Integer publisherId) {
        return publisherService.findById(publisherId);
    }

}
