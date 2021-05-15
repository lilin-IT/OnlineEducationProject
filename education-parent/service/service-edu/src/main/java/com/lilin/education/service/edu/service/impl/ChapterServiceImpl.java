package com.lilin.education.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lilin.education.service.edu.entity.Chapter;
import com.lilin.education.service.edu.entity.Video;
import com.lilin.education.service.edu.entity.vo.ChapterVo;
import com.lilin.education.service.edu.entity.vo.VideoVo;
import com.lilin.education.service.edu.mapper.ChapterMapper;
import com.lilin.education.service.edu.mapper.VideoMapper;
import com.lilin.education.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    VideoMapper videoMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeChapterById(String id) {
        //根据courseId删除video课时
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("chapter_id",id);
        videoMapper.delete(queryWrapper);
        //删除章节
        return  this.removeById(id);


    }

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        //获取章节信息列表
        QueryWrapper<Chapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        chapterQueryWrapper.orderByAsc("sort","id");
        List<Chapter> chapterList= baseMapper.selectList(chapterQueryWrapper);
        //获取课时信息列表
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoQueryWrapper.orderByAsc("sort","id");
        List<Video> videoList= videoMapper.selectList(videoQueryWrapper);
        System.out.println("ChapterServiceImpl中找到的课时信息列表：videoList:"+videoList);
        //组装章节列表
        List<ChapterVo> chapterVoList=new ArrayList<>();
        for (int i = 0; i < chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVoList.add(chapterVo);
            //组装章节列表：List<ChapterVo>
            List<VideoVo> videoVoList=new ArrayList<>();
            for (int j = 0; j < videoList.size(); j++) {
                Video video=videoList.get(j);
                if (chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
                
            }
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }
}
