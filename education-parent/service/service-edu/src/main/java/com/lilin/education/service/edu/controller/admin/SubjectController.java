package com.lilin.education.service.edu.controller.admin;


import com.lilin.education.common.base.result.R;
import com.lilin.education.common.base.result.ResultCodeEnum;
import com.lilin.education.common.base.util.ExceptionUtils;
import com.lilin.education.service.base.exception.EducationException;
import com.lilin.education.service.edu.entity.vo.SubjectVo;
import com.lilin.education.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@CrossOrigin
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/admin/edu/subject")
@Slf4j
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    @ApiOperation("Excel批量导入课程分类")
    @PostMapping("import")
    public R batchImport(@ApiParam(value = "Excel文件",required = true) @RequestParam("file") MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            return R.ok().message("批量导入成功！");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            e.printStackTrace();
            throw new EducationException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }
    @ApiOperation("嵌套数据列表")
    @GetMapping("nested-list")
    public R nestedList(){
        System.out.println("获取数据嵌套列表！");
        List<SubjectVo> subjectVoList=subjectService.nestedList();
        System.out.println("嵌套数据列表："+subjectVoList);
        return R.ok().data("items",subjectVoList);
    }
}

