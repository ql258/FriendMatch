package com.ql.friendmatch.service;

import com.ql.friendmatch.model.domain.ImgFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface FileService extends IService<ImgFile> {

    String upload(MultipartFile multipartFile);
}
