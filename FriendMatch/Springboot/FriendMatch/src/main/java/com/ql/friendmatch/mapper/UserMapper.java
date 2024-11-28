package com.ql.friendmatch.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ql.friendmatch.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ql.friendmatch.model.vo.ExportUserVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.ql.friendmatch.model.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select id,username,profile,userAccount,gender,phone,email from `user` ORDER BY id")
    List<ExportUserVO> list();
}




