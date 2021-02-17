package com.lilin.education.service.edu.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lilin.education.common.base.result.R;
import com.lilin.education.service.base.model.BaseEntity;
import com.lilin.education.service.edu.entity.Teacher;
import com.lilin.education.service.edu.entity.vo.TeacherQueryVo;
import com.lilin.education.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@CrossOrigin //允许跨域
@Api(description = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;
    @ApiOperation("所有讲师列表")
    @GetMapping("list")
    public R listAll(){
        List<Teacher> teachers=teacherService.list();
        return R.ok().data("items", teachers);
    }
    @ApiOperation(value = "根据ID删除讲师",notes = "根据ID删除讲师，逻辑删除")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam("讲师ID") @PathVariable String id){
        //删除讲师头像
        boolean b = teacherService.removeAvatarById(id);
        System.out.println("删除讲师头像是否成功："+b);
        //删除讲师
        boolean result= teacherService.removeById(id);
        if (result){
            return R.ok().message("删除成功！");

        }else {
            return R.error().message("数据不存在！");
        }
    }
    @ApiOperation("讲师分页列表")
    @GetMapping("list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码",required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数",required = true) @PathVariable Long limit,
                      @ApiParam("讲师列表查询对象") TeacherQueryVo teacherQueryVo){
        Page<Teacher> pagePapram=new Page<>(page,limit);
        IPage<Teacher> pageModel= teacherService.selectPage(pagePapram,teacherQueryVo);
        List<Teacher> records= pageModel.getRecords();
        long total= pageModel.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("新增讲师")
    @PostMapping("save")
    public R save(@ApiParam("讲师对象")@RequestBody Teacher teacher){
        teacherService.save(teacher);
        return R.ok().message("保存成功！");
    }
    @ApiOperation("更改讲师")
    @PutMapping("update")
    public R update(@ApiParam("讲师对象")@RequestBody Teacher teacher){
        boolean result = teacherService.updateById(teacher);
        if (result){
            return R.ok().message("修改成功！");
        }else {
            return R.error().message("数据不存在！");
        }
    }
    @ApiOperation("根据ID获取讲师信息")
    @GetMapping("get/{id}")
    public R getById(@ApiParam("讲师对象")@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        if (teacher!=null){
            return R.ok().data("item",teacher);
        }else {
            return  R.error().message("数据不存在");
        }
    }
    @ApiOperation(value = "根据ID列表删除讲师")
    @DeleteMapping("batch-remove")
    public R removeRows(
            @ApiParam(value = "讲师ID列表",required = true)
            @RequestBody List<String> idList){
        boolean result= teacherService.removeByIds(idList);
        if (result){
            return R.ok().message("删除成功！");
        }else {
            return R.error().message("数据不存在！");
        }
    }
    @ApiOperation("根据关键字查询讲师列表")
    @GetMapping("list/name/{key}")
    public R selectNameListByKey(@ApiParam(value = "关键字",required = true)
                                 @PathVariable String key){
        List<Map<String,Object>> nameList= teacherService.selectNameList(key);
        return R.ok().data("nameList",nameList);
    }
}

