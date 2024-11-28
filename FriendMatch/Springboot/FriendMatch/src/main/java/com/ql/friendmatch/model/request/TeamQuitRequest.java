package com.ql.friendmatch.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamQuitRequest implements Serializable {
    private static final long serialVersionUID = 903788749438697656L;
    /**
     * id
     */
    private Long teamId;
}
