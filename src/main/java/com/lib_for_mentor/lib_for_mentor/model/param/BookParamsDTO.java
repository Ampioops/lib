package com.lib_for_mentor.lib_for_mentor.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Schema(description = "Параметры книги")
public class BookParamsDTO {
    @Schema(description = "Название содержит строку")
    private String titleCont;   //contains

    @Schema(description = "Имя автора содержит строку")
    private String authorNameCont;

    @Schema(description = "Больше чем год издания")
    private Integer publishedGt;    //greaterThen

    @Schema(description = "Меньше чем год издания")
    private Integer publishedLt;    //lessThen

    @Schema(description = "Год издания")
    private Integer publishedEq;    //equals

    @Schema(description = "Больше чем страниц")
    private Integer pagesGt;

    @Schema(description = "Меньше чем страниц")
    private Integer pagesLt;
}
