package com.ql.friendmatch.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamJoinRequest implements Serializable {
    private static final long serialVersionUID = -5906653513559382911L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;

}
