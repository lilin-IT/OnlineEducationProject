package com.lilin.education.service.edu.service.impl;

import com.lilin.education.service.edu.entity.Course;
import com.lilin.education.service.edu.entity.CourseDescription;
import com.lilin.education.service.edu.entity.form.CourseInfoForm;
import com.lilin.education.service.edu.mapper.CourseDescriptionMapper;
import com.lilin.education.service.edu.mapper.CourseMapper;
import com.lilin.education.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionMapper descriptionMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存course
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);
        //报错CourseDescription
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        descriptionMapper.insert(courseDescription);

        return course.getId();
    }
}
