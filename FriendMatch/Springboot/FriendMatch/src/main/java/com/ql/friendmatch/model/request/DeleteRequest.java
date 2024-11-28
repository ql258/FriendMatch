package com.ql.friendmatch.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = -7156488745770172445L;
    private Integer id;
}
