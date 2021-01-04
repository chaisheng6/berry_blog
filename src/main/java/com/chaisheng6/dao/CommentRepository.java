package com.chaisheng6.dao;

import com.chaisheng6.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByBlog_Id(Long id,Sort sort);

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
