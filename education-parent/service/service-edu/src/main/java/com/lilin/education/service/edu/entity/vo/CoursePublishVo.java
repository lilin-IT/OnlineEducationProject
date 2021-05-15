package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 5098007067704414810L;
    private String id;
    private String title;
    private String cover;
    private String lessonNum;
    private String subjectParentTitle;
    private String subjectTitle;
    private String teacherName;
    private String price; //只用于显示
}
