package com.ql.friendmatch;

import com.ql.friendmatch.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
class FriendMatchApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("shayuString", "fish");
        valueOperations.set("shayuInt", 1);
        valueOperations.set("shayuDouble", 2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("shayu");
        valueOperations.set("shayuUser", user);

        // 查
        Object shayu = valueOperations.get("shayuString");
        Assertions.assertTrue("fish".equals((String) shayu));
        shayu = valueOperations.get("shayuInt");
        Assertions.assertTrue(1 == (Integer) shayu);
        shayu = valueOperations.get("shayuDouble");
        Assertions.assertTrue(2.0 == (Double) shayu);
        System.out.println(valueOperations.get("shayuUser"));
        valueOperations.set("shayuString", "fish");


    }

}
