package com.lilin.education.service.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lilin.education.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lilin.education.service.edu.entity.vo.CoursePublishVo;
import com.lilin.education.service.edu.entity.vo.CourseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {



    List<CourseVo> selectPageByCourseQueryVo(Page<CourseVo> pageParam,
                                             @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    CoursePublishVo selectCoursePublishVoById();
}
