package com.ql.friendmatch.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName file
 */
@TableName(value = "file")
@Data
public class ImgFile implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String fileName;

    private Date uploadTime;

    private String url;

    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}