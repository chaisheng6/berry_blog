package com.chaisheng6.pojo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


import java.util.*;

@Entity(name = "t_type")
public class Type {
    public Type() {
    }

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名不能为空")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> list=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getList() {
        return list;
    }

    public void setList(List<Blog> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
