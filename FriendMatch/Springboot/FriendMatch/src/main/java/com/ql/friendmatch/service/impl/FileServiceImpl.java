package com.ql.friendmatch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ql.friendmatch.common.ErrorCode;
import com.ql.friendmatch.exception.BusinessException;
import com.ql.friendmatch.model.domain.ImgFile;
import com.ql.friendmatch.service.FileService;
import com.ql.friendmatch.mapper.FileMapper;
import com.ql.friendmatch.utils.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static com.ql.friendmatch.constant.FileConstant.BUCKET_NAME;
import static com.ql.friendmatch.constant.FileConstant.MINIO_PATH;

/**
 *
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, ImgFile>
        implements FileService {
    final long ONE_SIZE = 1024 * 1024 * 3;
    @Resource
    private MinioUtil minioUtil;
    @Resource
    private FileMapper fileMapper;

    @Override
    public String upload(MultipartFile multipartFile) {
        validFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String newName = UUID.randomUUID() + suffix;
        String filepath = String.format("%s/%s/%s", MINIO_PATH, BUCKET_NAME, newName);
        File file = null;
        try {
            ImgFile imgFile = new ImgFile();
            imgFile.setFileName(newName);
            imgFile.setUploadTime(new Date());
//            boolean save = fileService.save(file1);
//            int save = fileMapper.updateById(imgFile);
            int insert = fileMapper.insert(imgFile);
            if (insert <= 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            minioUtil.putObject(BUCKET_NAME, newName, multipartFile.getInputStream(), multipartFile.getContentType());
            //返回可访问地址
            return filepath;
        } catch (Exception e) {
            System.out.println("file upload error, filepath = " + filepath + ", error = " + e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                //删除临时文件
                boolean delete = file.delete();
                if (!delete) System.out.println("file delete error, filepath = [l" + filepath);
            }
        }
    }

    //文件校验
    public void validFile(MultipartFile file) {
        //文件大小
        long fileSize = file.getSize();
        //文件后缀
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
        //判断
        if (fileSize > ONE_SIZE) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过3M");
        }
        if (!Arrays.asList(".jpeg", ".jpg", ".svg", ".png", ".webp", ".gif").contains(suffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
        }
    }
}




