package com.lib_for_mentor.lib_for_mentor.exception;

import org.common.common_utils.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler({AuthorNotFoundException.class, BookNotFoundException.class, UserNotFoundException.class, PublisherNotFoundException.class, GenreNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
