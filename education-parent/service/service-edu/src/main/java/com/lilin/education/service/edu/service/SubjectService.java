package com.lilin.education.service.edu.service;

import com.lilin.education.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lilin.education.service.edu.entity.vo.SubjectVo;
import jdk.internal.util.xml.impl.Input;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
public interface SubjectService extends IService<Subject> {
    void batchImport(InputStream inputStream);


    List<SubjectVo> nestedList();
}
