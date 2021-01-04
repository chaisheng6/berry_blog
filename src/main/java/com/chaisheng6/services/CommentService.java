package com.chaisheng6.services;

import com.chaisheng6.pojo.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    void saveComment(Comment comment);


}
