package com.chaisheng6.services;

import com.chaisheng6.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //新增
    Type saveType(Type type);

    //查询
    Type getType(Long id);

    //分页查询
    Page<Type> listType(Pageable pageable);

    //前端首页显示top type
    List<Type> listTypeTop(Integer size);

    //修改
    Type updateType(Long id,Type type);

    //删除
    void deleteType(Long id);

    //通过名称查询一个type
    Type getTypeByName(String name);

    List<Type> listType();
}
