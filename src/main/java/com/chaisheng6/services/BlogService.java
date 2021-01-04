package com.chaisheng6.services;

import com.chaisheng6.pojo.Blog;
import com.chaisheng6.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long TagId,Pageable pageable);

    Blog saveBlog(Blog blog);

    //查询
    Page<Blog> listBlog(String query,Pageable pageable);

    //首页推荐博客
    List<Blog> listRecommendBlogTop(Integer size);

    //归档
    //String代表年份
    Map<String,List<Blog>> archiveBlog();

    //所有blog的数量
    Long blogCount();

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
