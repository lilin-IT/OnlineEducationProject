package com.lilin.education.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.Course;
import com.lilin.education.service.edu.entity.Teacher;
import com.lilin.education.service.edu.entity.form.CourseInfoForm;
import com.lilin.education.service.edu.entity.vo.CoursePublishVo;
import com.lilin.education.service.edu.entity.vo.CourseQueryVo;
import com.lilin.education.service.edu.entity.vo.CourseVo;
import com.lilin.education.service.edu.entity.vo.TeacherQueryVo;
import com.lilin.education.service.edu.service.CourseService;
import com.lilin.education.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private VideoService videoService;
    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "课程基本信息",required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        String courseId= courseService.saveCourseInfo(courseInfoForm);
        System.out.println("保存课程ID："+courseId);
        return R.ok().data("courseId",courseId).message("保存课程成功！");
    }
    @ApiOperation("根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(@ApiParam(value = "课程ID",required = true)
                                 @PathVariable String id){
        System.out.println("回显信息ID："+id);
        CourseInfoForm courseInfoForm= courseService.getCourseInfoById(id);
        System.out.println("回显信息："+courseInfoForm);
        if (courseInfoForm!=null){
            return R.ok().data("item",courseInfoForm);
        }else {
            return R.ok().message("数据不存在");
        }
    }
    @ApiOperation("更新课程")
    @PutMapping("update-course-info")
    public R getById(@ApiParam(value = "课程ID",required = true)
                                 @RequestBody CourseInfoForm courseInfoForm){
        System.out.println("更新课程："+courseInfoForm);
        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok().message("修改成功!");
    }
    @ApiOperation("课程分页列表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码",required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数",required = true) @PathVariable Long limit,
                      @ApiParam("讲师列表查询对象") CourseQueryVo courseQueryVo) {
        System.out.println("课程列表,page:"+page+"limit:"+limit);
        IPage<CourseVo> pageModel = courseService.selectPage(page, limit, courseQueryVo);
        System.out.println("查询出的信息：pagemModel:"+pageModel);
        List<CourseVo> records = pageModel.getRecords();
        System.out.println("查询记录："+records);
        long total = pageModel.getTotal();
        System.out.println("total:"+total);
        return R.ok().data("total", total).data("rows", records);
    }
    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "课程ID",required = true) @PathVariable String id){
        //删除课程视频
        videoService.removeMediaVideoByCourseId(id);
        //此处调用vod中的删除视频文件的接口
        //删除课程封面
        boolean b = courseService.removeCoverById(id);
        System.out.println("删除讲师头像是否成功："+b);
        //删除课程
        boolean result= courseService.removeCourseById(id);
        if (result){
            return R.ok().message("删除成功！");

        }else {
            return R.error().message("数据不存在！");
        }
    }
    @ApiOperation("根据ID获取课程发布信息")
    @GetMapping("course-publish/{id}")
    public R getCoursePublishVoById(@ApiParam(value = "课程ID",required = true)
                                            @PathVariable String id){
        CoursePublishVo coursePublishVo= courseService.getCoursePublishVoById();
        if (coursePublishVo!=null){
                return R.ok().data("item",coursePublishVo);
        }else {
            return R.error().message("数据不存在！");
        }
    }
    @ApiOperation("根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(@ApiParam(value = "课程ID",required = true)
                                       @PathVariable String id){
       boolean result=  courseService.publishCourseById(id);
       if (result){
           return R.ok().message("发布成功！");
       }else {
           return R.error().message("数据不存在！");
       }
    }
}

