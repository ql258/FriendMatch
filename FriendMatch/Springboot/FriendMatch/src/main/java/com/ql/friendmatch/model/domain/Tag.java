package com.ql.friendmatch.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName tag
 */
@TableName(value = "tag")
@Data
public class Tag implements Serializable {
    private Long id;

    private String tagName;

    private Long userId;

    private Long parentId;

    private Integer isParent;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}