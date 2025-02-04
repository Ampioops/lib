package com.lib_for_mentor.lib_for_mentor.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Параметры автора")
public class AuthorParamsDTO {
    @Schema(description = "Имя содержит строку")
    private String firstNameCont;

    @Schema(description = "Фамилия содержит строку")
    private String lastNameCont;

    @Schema(description = "Список книг содержит книгу с идентификатором")
    private Integer bookIdCont;
}
