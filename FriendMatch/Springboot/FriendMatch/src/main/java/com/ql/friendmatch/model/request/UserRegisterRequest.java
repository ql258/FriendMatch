package com.ql.friendmatch.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -8069551994354790628L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
