package com.ql.friendmatch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ql.friendmatch.model.domain.Tag;
import com.ql.friendmatch.service.TagService;
import com.ql.friendmatch.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {
    @Resource
    private TagMapper tagMapper;


    @Override
    public List<String> childrenList(Long id) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("tagName").eq("parentId", id).eq("isParent", 0);
//        queryWrapper.eq("parentId", id);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        List<String> tagNames = tags.stream()
                .map(Tag::getTagName) // 替换为需要提取的属性
                .collect(Collectors.toList());
        return tagNames;
    }
}




