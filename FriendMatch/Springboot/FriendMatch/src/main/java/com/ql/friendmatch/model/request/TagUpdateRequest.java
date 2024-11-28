package com.ql.friendmatch.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TagUpdateRequest implements Serializable {
    private static final long serialVersionUID = 257051368281229457L;

    private List<String> tags;
}
