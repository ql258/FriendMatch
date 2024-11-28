package com.ql.friendmatch;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.ql.friendmatch.mapper")
public class FriendMatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendMatchApplication.class, args);
    }

}
