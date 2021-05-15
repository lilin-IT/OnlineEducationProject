package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class VideoVo implements  Serializable {
    private static final long serialVersionUID = 124894705899971719L;
    private String id;
    private String title;
    private boolean free ;
    private String sort; //排序
    private String videoSourceId;



}
