package com.chaisheng6.services;

import com.chaisheng6.NotFindException;
import com.chaisheng6.dao.BlogRepository;
import com.chaisheng6.pojo.Blog;
import com.chaisheng6.pojo.Type;
import com.chaisheng6.util.MarkdownUtils;
import com.chaisheng6.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join=root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Pageable pageable = PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        //返回一个年份
        Map<String,List<Blog>> map=new HashMap<>();
        List<String> years=blogRepository.findGroupYear();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long blogCount() {
        return blogRepository.count();
    }


    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.findById(id).orElse(null);
        if (blog1 == null) {
            throw new NotFindException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, blog1);
        return blogRepository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


}
