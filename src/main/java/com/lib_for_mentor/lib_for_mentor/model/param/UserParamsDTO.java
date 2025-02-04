package com.lib_for_mentor.lib_for_mentor.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Параметры пользователя")
public class UserParamsDTO {
    private String firstNameCont;
    private String lastNameCont;
    private Integer bookIdCont;
    private String emailCont;
}
