package com.ql.friendmatch.service;

import com.ql.friendmatch.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ql.friendmatch.model.vo.ExportUserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
public interface UserService extends IService<User> {

    //用户脱敏
    User getSafetyUser(User originUser);

    long userRegister(String userAccount, String userPassword, String checkPassword);

    User login(String userAccount, String userPassword, HttpServletRequest request);

    int logout(HttpServletRequest request);

    List<ExportUserVO> export();

    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User loginUser);

    List<User> searchUsersByTags(List<String> tagNameList);

    User getLoginUser(HttpServletRequest request);

    int updateUser(User user, User loginUser);

    List<User> matchUser(long num, User loginUser);

    boolean changePassword(String userAccount, String userPassword, String checkPassword);
}
