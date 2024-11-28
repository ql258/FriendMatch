package com.ql.friendmatch.service;

import com.ql.friendmatch.model.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface TagService extends IService<Tag> {
    List<String> childrenList(Long id);

//    List<String> childrenList();
}
