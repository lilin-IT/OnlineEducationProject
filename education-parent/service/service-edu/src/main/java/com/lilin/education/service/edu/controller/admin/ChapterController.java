package com.lilin.education.service.edu.controller.admin;


import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.Chapter;
import com.lilin.education.service.edu.entity.vo.ChapterVo;
import com.lilin.education.service.edu.service.ChapterService;
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
@Api(description = "章节管理")
@RestController
@Slf4j
@RequestMapping("/admin/edu/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @Autowired
    VideoService videoService;
    @ApiOperation("新增章节")
    @PostMapping("save")
    public R save(@ApiParam(value = "章节对象",required = true)
                              @RequestBody Chapter chapter){
        boolean result= chapterService.save(chapter);
        if (result){
            return R.ok().message("保存成功！");
        }else {
            return R.error().message("保存失败！");
        }
    }
    @ApiOperation("根据id查询章节")
    @GetMapping("get/{id}")
    public R getById(
            @ApiParam(value="章节id", required = true)
            @PathVariable String id){
      Chapter chapter= chapterService.getById(id);
      System.out.println("ChapterController中getById根据id："+id+"查询到的chapter:"+chapter);
      if (chapter!=null){
          return R.ok().data("item",chapter);
      }else {
          return R.error().message("数据不存在！");
      }
    }
    @ApiOperation("根据id章节")
    @PutMapping("update")
    public R updateById(@ApiParam(value = "章节对象",required = true)
                                    @RequestBody Chapter chapter){
        boolean result= chapterService.updateById(chapter);
        if (result){
            return R.ok().message("修改成功！");
        }else {
            return R.error().message("修改失败,数据不存在！");
        }
    }
    @ApiOperation("根据id删除章节")
    @DeleteMapping("remove/{id}")
    public R removeById(@ApiParam(value = "章节ID",required = true)
                                    @PathVariable String id){
        //删除课程视频
        videoService.removeMediaVideoByChapterId(id);
        //删除章节
        boolean result= chapterService.removeChapterById(id);
        if (result){
            return R.ok().message("删除成功！");
        }else {
            return R.error().message("删除失败,数据不存在！");
        }
    }
    @ApiOperation("嵌套章节数据列表")
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(value = "课程ID",required = true)
                    @PathVariable String courseId){
        System.out.println("获取课程id为："+courseId+"的章节！");
        List<ChapterVo> chapterVoList= chapterService.nestedList (courseId);
        System.out.println("找到课程章节信息："+chapterVoList);

        return R.ok().data("items",chapterVoList);


    }

}

