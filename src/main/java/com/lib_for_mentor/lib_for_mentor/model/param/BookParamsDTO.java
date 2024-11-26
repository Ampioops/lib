package com.lib_for_mentor.lib_for_mentor.model.param;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookParamsDTO {
    private String titleCont;   //contains
    private String authorCont;
    private Integer publishedGt;    //greaterThen
    private Integer publishedLt;    //lessThen
    private Integer publishedEq;    //equals
    private Integer pagesGt;
    private Integer pagesLt;
}
