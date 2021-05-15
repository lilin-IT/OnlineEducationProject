package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 8668054174542169197L;
    private String id;
    private String title;
    private String sort;
    private List<VideoVo> children=new ArrayList<>();
}
