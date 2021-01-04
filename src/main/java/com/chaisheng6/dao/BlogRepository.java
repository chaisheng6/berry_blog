package com.chaisheng6.dao;

import com.chaisheng6.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query(value="select b from com.chaisheng6.pojo.Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Query(value="select b from com.chaisheng6.pojo.Blog b where b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Query(value="select EXTRACT(year FROM update_time) as year from t_blog GROUP BY year order by year desc ",nativeQuery=true)
    List<String> findGroupYear();

    @Query(value="select * from t_blog where EXTRACT(year from update_time) = ?1",nativeQuery=true)
    List<Blog> findByYear(String year);
}
