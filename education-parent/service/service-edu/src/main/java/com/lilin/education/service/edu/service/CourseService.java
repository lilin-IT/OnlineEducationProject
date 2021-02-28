package com.lilin.education.service.edu.service;

import com.lilin.education.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lilin.education.service.edu.entity.form.CourseInfoForm;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);
}
