package com.lilin.education.service.oss.controller.admin;

import com.lilin.education.common.base.result.R;
import com.lilin.education.common.base.result.ResultCodeEnum;
import com.lilin.education.common.base.util.ExceptionUtils;
import com.lilin.education.service.base.exception.EducationException;
import com.lilin.education.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(description = "阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;
    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(value = "文件",required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "模块",required = true)
            @RequestParam("module") String module) {
        try {
            InputStream inputStream=file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url",uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new EducationException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }
    @ApiOperation(value = "文件删除")
    @DeleteMapping("remove")
    public R removeFile(
           @ApiParam(value = "要删除的文件url路径",required = true)
           @RequestBody String url){
        System.out.println("准备删除文件及头像！url:"+url);
        fileService.removeFile(url);
        return R.ok().message("删除成功！");
    }
}
