package com.lilin.education.service.edu.controller.api;

import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.Course;
import com.lilin.education.service.edu.entity.vo.WebCourseQueryVo;
import com.lilin.education.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(description = "课程")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {
    @Autowired
    private CourseService courseService;
    @ApiOperation("课程列表")
    @GetMapping("list")
    public R pageList(@ApiParam(value = "查询对象",required = true) WebCourseQueryVo webCourseQueryVo){
        List<Course> courseList= courseService.webSelectList(webCourseQueryVo);
        return R.ok().data("courseList",courseList);
    }
}
