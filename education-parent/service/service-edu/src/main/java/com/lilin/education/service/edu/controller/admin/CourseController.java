package com.lilin.education.service.edu.controller.admin;


import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.form.CourseInfoForm;
import com.lilin.education.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@CrossOrigin
@Api(description = "课程管理")
@RestController
@RequestMapping("/admin/edu/course")
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;
    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "课程基本信息",required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        String courseId= courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId).message("保存课程成功！");
    }
}

