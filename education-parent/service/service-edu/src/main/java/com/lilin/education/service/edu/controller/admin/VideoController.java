package com.lilin.education.service.edu.controller.admin;


import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.Video;
import com.lilin.education.service.edu.entity.vo.VideoVo;
import com.lilin.education.service.edu.feign.VodMediaService;
import com.lilin.education.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@CrossOrigin
@Api(description = "课时管理")
@RestController
@Slf4j
@RequestMapping("/admin/edu/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("新增课时")
    @PostMapping("save")
    public R save(@ApiParam(value = "课时对象",required = true)
                              @RequestBody Video video){
        System.out.println("执行VideoController中的save方法,保存video:"+video);
        boolean result= videoService.save(video);
        if (result){
            return R.ok().message("保存成功！");
        }else {
            return R.error().message("保存失败！");
        }
    }
    @ApiOperation("根据id查询课时")
    @GetMapping("get/{id}")
    public R getById(@ApiParam(value = "课时ID",required = true)
                                 @PathVariable String id){
        Video video= videoService.getById(id);
        if (video!=null){
            return R.ok().data("item",video);
        }else {
            return R.error().message("数据不存在！");
        }
    }
    @ApiOperation("根据id修改课时")
    @PutMapping("update")
    public R  update(@ApiParam(value = "课时对象",required = true)
                                 @RequestBody Video video){
        boolean result= videoService.updateById(video);
        if (result){
            return R.ok().message("修改成功！");
        }else {
            return  R.error().message("更新失败");
        }

    }
    @ApiOperation("根据id删除课时")
    @DeleteMapping("remove/{id}")
    public R  removeById(@ApiParam(value = "课时id",required = true)
                     @PathVariable String id){
        //删除视频
        videoService.removeMediaVideoById(id);
        boolean result= videoService.removeById(id);
        if (result){
            return R.ok().message("删除成功！");
        }else {
            return  R.error().message("删除失败");
        }

    }

}

