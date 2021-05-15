package com.lilin.education.service.edu.service;

import com.lilin.education.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lilin.education.service.edu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
public interface ChapterService extends IService<Chapter> {

    boolean  removeChapterById(String id);

    List<ChapterVo>  nestedList(String courseId);
}
