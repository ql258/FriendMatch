package com.ql.friendmatch.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
public class ExportUserVO {
    private Integer id;
    private String username;
    private String userAccount;
    private String gender;
    private String phone;
    private String email;
}
