package com.ql.friendmatch.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.ql.friendmatch.common.ErrorCode;
import com.ql.friendmatch.exception.BusinessException;
import com.ql.friendmatch.model.domain.User;
import com.ql.friendmatch.model.vo.ExportUserVO;
import com.ql.friendmatch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/excel")
@Slf4j
@Api("excel表接口")
public class ExcelController {
    @Resource
    private UserService userService;

    @ApiOperation("数据导入")
    @PostMapping("/import")
    public void importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<User> userList = reader.readAll(User.class);
        try{
            userService.saveBatch(userList);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.IMPORT_ERROR);
        }
    }
    @ApiOperation("数据导出")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);
        List<ExportUserVO> list = userService.export();
        if (list==null){
            throw new BusinessException(ErrorCode.EXPORT_ERROR);
        }
        List<ExportUserVO> vo = list.stream()
                .peek(exportUserVO -> {
                    if (exportUserVO.getUserAccount()==null){
                        exportUserVO.setUserAccount("空");
                    }
                    if (exportUserVO.getGender()==null){
                        exportUserVO.setGender("未知");
                    }else if ("1".equals(exportUserVO.getGender())){
                        exportUserVO.setGender("男");
                    }else if ("0".equals(exportUserVO.getGender())){
                        exportUserVO.setGender("女");
                    }
                    if (exportUserVO.getUsername()==null){
                        exportUserVO.setUsername("空");
                    }
                    if (exportUserVO.getEmail()==null){
                        exportUserVO.setEmail("空");
                    }
                    if (exportUserVO.getPhone()==null){
                        exportUserVO.setPhone("空");
                    }
                } ).collect(Collectors.toList());
        System.out.println(vo);
        writer.write(vo, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息表", "UTF-8") + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();

        writer.flush(outputStream, true);
        writer.close();
        outputStream.flush();
        outputStream.close();
    }
}
