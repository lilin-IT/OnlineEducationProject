package com.lilin.education.service.edu.service.impl;

import com.lilin.education.service.edu.entity.Comment;
import com.lilin.education.service.edu.mapper.CommentMapper;
import com.lilin.education.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author lilin
 * @since 2020-12-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
