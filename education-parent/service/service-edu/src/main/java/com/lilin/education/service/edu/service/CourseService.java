package com.lilin.education.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lilin.education.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lilin.education.service.edu.entity.form.CourseInfoForm;
import com.lilin.education.service.edu.entity.vo.CoursePublishVo;
import com.lilin.education.service.edu.entity.vo.CourseQueryVo;
import com.lilin.education.service.edu.entity.vo.CourseVo;
import com.lilin.education.service.edu.entity.vo.WebCourseQueryVo;

import java.util.List;

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

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo);

    boolean removeCoverById(String id);

    boolean removeCourseById(String id);

    CoursePublishVo getCoursePublishVoById();

    boolean  publishCourseById(String id);

    List<Course>  webSelectList(WebCourseQueryVo webCourseQueryVo);
}
