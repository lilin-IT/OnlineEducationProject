package com.lilin.education.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lilin.education.common.base.result.R;
import com.lilin.education.service.edu.entity.Course;
import com.lilin.education.service.edu.entity.Teacher;
import com.lilin.education.service.edu.entity.vo.TeacherQueryVo;
import com.lilin.education.service.edu.feign.OssFileService;
import com.lilin.education.service.edu.mapper.CourseMapper;
import com.lilin.education.service.edu.mapper.TeacherMapper;
import com.lilin.education.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    OssFileService ossFileService;
    @Autowired
    CourseMapper courseMapper;
    @Override
    public IPage<Teacher> selectPage(Page<Teacher> pagePapram, TeacherQueryVo teacherQueryVo) {
        //显示分页查询列表
        //1.排序：安装sort排序
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        //2.分页查询
        if (teacherQueryVo==null){
            return baseMapper.selectPage(pagePapram,queryWrapper);
        }
        //3.条件查询
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.likeRight("name",name);
        }
        if (level!=null){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(joinDateBegin)){
            queryWrapper.ge("join_date",joinDateBegin);
        }
        if (!StringUtils.isEmpty(joinDateEnd)){
            queryWrapper.le("join_date",joinDateEnd);
        }

        return baseMapper.selectPage(pagePapram,queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectNameList(String key) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name",key);
        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);

        return list;
    }

    /**
     * 删除讲师头像
     * @param id
     * @return
     */
    @Override
    public boolean removeAvatarById(String id) {
        //根据id讲师avatar的url
        Teacher teacher = baseMapper.selectById(id);
        if(teacher!=null){
            String avatar = teacher.getAvatar();
            System.out.println("讲师头像："+avatar);
            if (!StringUtils.isEmpty(avatar)){
              R r= ossFileService.removeFile(avatar);
              return  r.getSuccess();
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> selectTeacherInfoById(String id) {

        Teacher teacher=baseMapper.selectById(id);
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",id);
        List<Course> courseList= courseMapper.selectList(courseQueryWrapper);
        Map<String,Object> map=new HashMap<>();
        map.put("teacher",teacher);
        map.put("courseList",courseList);
        return map;
    }


}
