package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CourseVo implements Serializable {
    private static final long serialVersionUID = -606261401598114578L;
    private String id;
    private String title;
    private String subjectParentTitle; //一级分类标题
    private String subjectTitle; //二级分类标题
    private String teacherName; //讲师姓名
    private String lessonNum;
    private String price;
    private String cover; //封面
    private String buyCount;
    private String viewCount;
    private String status;
    private String gmtCreate;


}
