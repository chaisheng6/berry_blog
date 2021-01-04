package com.chaisheng6.services;

import com.chaisheng6.NotFindException;
import com.chaisheng6.dao.TagRepository;
import com.chaisheng6.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public List<Tag> listTag(String ids) { //1,2,3
        List<Long> longs = converToList(ids);
        List<Tag> tags = new ArrayList<>();
        for (Long aLong : longs) {
            Tag tag = tagRepository.findById(aLong).get();
            tags.add(tag);
        }
        return tags;
    }


    @Override
    public List<Tag> listTagTop(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Direction.DESC,"blogs.size"));
        return tagRepository.findTop(pageable);
    }

    //把前端传来的ids转为一个list数组
    private List<Long> converToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id,Tag tag) {
        Tag tag1 = tagRepository.findById(id).orElse(null);
        if (tag1==null){
            throw new NotFindException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
