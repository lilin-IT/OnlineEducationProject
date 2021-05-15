package com.lilin.education.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.*;
import com.lilin.education.service.edu.entity.form.CourseInfoForm;
import com.lilin.education.service.edu.entity.vo.CoursePublishVo;
import com.lilin.education.service.edu.entity.vo.CourseQueryVo;
import com.lilin.education.service.edu.entity.vo.CourseVo;
import com.lilin.education.service.edu.entity.vo.WebCourseQueryVo;
import com.lilin.education.service.edu.feign.OssFileService;
import com.lilin.education.service.edu.mapper.*;
import com.lilin.education.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionMapper descriptionMapper;
    @Autowired
    private OssFileService ossFileService;
    @Autowired
    private VideoMapper videoMapper; //课时
    @Autowired
    private ChapterMapper chapterMapper; // 章节
    @Autowired
    private CommentMapper commentMapper; //评论
    @Autowired
    private CourseCollectMapper courseCollectMapper; //收藏

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //保存course
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);
        //保存CourseDescription
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        descriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        //根据ID获取course
        Course course = baseMapper.selectById(id);
        if (course==null){
            return null;
        }
        //根据id获取CourseDescription
        CourseDescription courseDescription = descriptionMapper.selectById(id);
        //组成成courseInfoForm
        CourseInfoForm courseInfoForm=new CourseInfoForm();
        BeanUtils.copyProperties(course,courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        //更新course
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoForm,course);
        baseMapper.updateById(course);

        //更新CourseDescription
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(courseInfoForm.getId());
        descriptionMapper.updateById(courseDescription);
    }

    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo) {
        //组装查询条件
        QueryWrapper<CourseVo> queryWrapper =new QueryWrapper<>();
        queryWrapper.orderByAsc("c.gmt_create");
        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("c.title",title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id",teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("c.subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("c.subject_id",subjectId);
        }
        //组装分页
        Page<CourseVo> pageParam = new Page<>(page, limit);
        //执行分页查询
        //只需要在mapper层传入封装好的分页组件即可，sql分页条件组装的过程由mp自动完成
        List<CourseVo> records= baseMapper.selectPageByCourseQueryVo(pageParam, queryWrapper);
        return pageParam.setRecords(records);


    }

    @Override
    public boolean removeCoverById(String id) {
        Course course=baseMapper.selectById(id);
        if (course!=null){
           String cover= course.getCover();
           if (!StringUtils.isEmpty(cover)){
              R r= ossFileService.removeFile(cover);
              return r.getSuccess();
           }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(String id) {
        //根据courseId删除video(课时）
        QueryWrapper<Video> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        videoMapper.delete(videoQueryWrapper);

        //根据courseId删除Chapter(章节)
        QueryWrapper<Chapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        chapterMapper.delete(chapterQueryWrapper);

        //根据courseId删除Comment(评论）
        QueryWrapper<Comment> commentQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        commentMapper.delete(commentQueryWrapper);

        //根据courseId删除CouseCollect（课程收藏）
        QueryWrapper<CourseCollect> courseCollectQueryWrapper=new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id",id);
        courseCollectMapper.delete(courseCollectQueryWrapper);
        //删除课程详情
        descriptionMapper.deleteById(id);
        //删除课程
        return this.removeById(id);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById() {
        return baseMapper.selectCoursePublishVoById();
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course=new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        //查询已发布的课程
        queryWrapper.eq("status",Course.COURSE_NORMAL);
        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",webCourseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())){
            queryWrapper.eq("subject_id",webCourseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())){
            queryWrapper.orderByDesc("price");
        }

        return baseMapper.selectList(queryWrapper);
    }
}
