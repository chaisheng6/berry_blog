package com.chaisheng6.dao;

import com.chaisheng6.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    @Query(value="select t from com.chaisheng6.pojo.Type t")
    List<Type> findTop(Pageable pageable);
}
