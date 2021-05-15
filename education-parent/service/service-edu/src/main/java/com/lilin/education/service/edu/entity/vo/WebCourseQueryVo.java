package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class  WebCourseQueryVo  implements  Serializable {
    private static final long serialVersionUID = -7768831754812444693L;
    private String subjectParentId;
    private String subjectId;
    private String buyCountSort;
    private String gmtCreateSort;
    private String priceSort;





}
