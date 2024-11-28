package com.ql.friendmatch.controller;

import com.ql.friendmatch.common.BaseResponse;
import com.ql.friendmatch.common.ErrorCode;
import com.ql.friendmatch.common.ResultUtils;
import com.ql.friendmatch.exception.BusinessException;
import com.ql.friendmatch.service.FileService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/file")
@Slf4j
@Api("文件接口")
@CrossOrigin(origins = "http://localhost:5173/")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile) {
        String path = fileService.upload(multipartFile);
        if (path == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success("上传成功");
    }

}
