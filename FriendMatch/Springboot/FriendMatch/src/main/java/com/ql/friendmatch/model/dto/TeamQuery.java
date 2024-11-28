package com.ql.friendmatch.model.dto;

import com.ql.friendmatch.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data

public class TeamQuery extends PageRequest {
    private static final long serialVersionUID = 447631296990017219L;
    /**
     * id
     */
    private Long id;

    /**
     * id 列表
     */
    private List<Long> idList;

    /**
     * 搜索关键词（同时对队伍名称和描述搜索）
     */
    private String searchText;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 用户id
     */
    private Long userId;

    private String imageUrl;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;
}
