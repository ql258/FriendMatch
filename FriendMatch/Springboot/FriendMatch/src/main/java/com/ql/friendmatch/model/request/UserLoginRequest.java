package com.ql.friendmatch.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -4567423251715015536L;
    private String userAccount;
    private String userPassword;
}
