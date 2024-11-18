package com.lib_for_mentor.lib_for_mentor.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    //Knigi??? Думаю нет тк мб добавить отдельный запрос на присвоение книги автору?
}
