package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CourseQueryVo implements Serializable{

    private static final long serialVersionUID = 866869271256653900L;
    private String title;
    private String teacherId;
    private String subjectParentId;
    private String subjectId;


}
