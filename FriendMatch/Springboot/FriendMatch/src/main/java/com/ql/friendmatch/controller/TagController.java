package com.ql.friendmatch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ql.friendmatch.common.BaseResponse;
import com.ql.friendmatch.common.ErrorCode;
import com.ql.friendmatch.common.ResultUtils;
import com.ql.friendmatch.exception.BusinessException;
import com.ql.friendmatch.model.domain.Tag;
import com.ql.friendmatch.model.domain.User;
import com.ql.friendmatch.model.request.TagUpdateRequest;
import com.ql.friendmatch.model.vo.TagVO;
import com.ql.friendmatch.service.TagService;
import com.ql.friendmatch.service.UserService;
import io.swagger.annotations.Api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
@Slf4j
@Api("标签接口")
@CrossOrigin(origins = "http://localhost:5173/")
public class TagController {
    @Resource
    private TagService tagService;
    @Resource
    private UserService userService;
    @Qualifier("redisTemplate")
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/list")
    public BaseResponse<List<TagVO>> list(@RequestParam String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isParent", 1);
        System.out.println(name);
        if (name != null) {
            queryWrapper.like("tagName", name);
        }
        List<Tag> father = tagService.list(queryWrapper);
        List<TagVO> tagVOList = new ArrayList<>();
        for (Tag tag : father) {
            TagVO tagVO = new TagVO();
            tagVO.setText(tag.getTagName());
            QueryWrapper<Tag> qw = new QueryWrapper<>();
            qw.select("tagName").eq("parentId", tag.getId()).eq("isParent", 0);
            List<String> list = tagService.list(qw).stream().map(Tag::getTagName).collect(Collectors.toList());
            List<Map<String, String>> mapList = new ArrayList<>();
            for (String item : list) {
                Map<String, String> map = new HashMap<>();
                map.put("text", item);
                map.put("id", item);
                mapList.add(map);
                tagVO.setChildren(mapList);
            }
            tagVOList.add(tagVO);
        }
        return ResultUtils.success(tagVOList);
    }

    @GetMapping("/show")
    public BaseResponse<List<String>> show(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        System.out.println(loginUser);
        User user = userService.getById(loginUser.getId());
        String tags = user.getTags();
        String str = tags.substring(1, tags.length() - 1);
        String[] data = str.split(", ");
        List<String> list = new ArrayList<>();
        for (String item : data) {
            list.add(item.replace("\"", ""));
        }
        System.out.println("show: " + list);
//        System.out.println(list);
        return ResultUtils.success(list);
    }

    @PutMapping("/update")
    public BaseResponse<String> update(HttpServletRequest request, @RequestBody TagUpdateRequest tagUpdateRequest) {
        User loginUser = userService.getLoginUser(request);
        List<String> tags = tagUpdateRequest.getTags();
        List<String> list = new ArrayList<>();
        for (String item : tags) {
            list.add("\"" + item + "\"");
        }
        String result = String.join(", ", list);
//        System.out.println(tags);
        System.out.println("[" + result + "]");
        result = "[" + result + "]";
        loginUser.setTags(result);
        boolean flag = userService.updateById(loginUser);
        if (!flag) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String redisKey = String.format("user:recommend:%s", loginUser.getId());
        redisTemplate.delete(redisKey);
        return ResultUtils.success("修改成功");
    }
}
