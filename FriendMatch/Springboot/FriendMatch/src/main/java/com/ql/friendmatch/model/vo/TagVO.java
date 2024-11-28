package com.ql.friendmatch.model.vo;

import com.ql.friendmatch.model.domain.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class TagVO implements Serializable {
    private static final long serialVersionUID = 3029358875241726495L;
    private String text;
    private List<Map<String, String>> children;
}
