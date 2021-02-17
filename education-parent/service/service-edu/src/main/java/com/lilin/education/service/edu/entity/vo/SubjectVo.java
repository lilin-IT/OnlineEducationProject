package com.lilin.education.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVo implements Serializable  {
    private static final long serialVersionUID = 8255498709553248448L;
    private String id;
    private String title;
    private String sort;
    private List<SubjectVo> children=new ArrayList<>();
}
