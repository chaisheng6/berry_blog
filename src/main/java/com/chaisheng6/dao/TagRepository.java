package com.chaisheng6.dao;

import com.chaisheng6.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);

    @Query(value="select t from com.chaisheng6.pojo.Tag t")
    List<Tag> findTop(Pageable pageable);

}
