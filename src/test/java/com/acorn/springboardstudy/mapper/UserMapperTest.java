package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    void findByUId() {
        UserDto user=userMapper.findByUId("user01");
        System.out.println("user = " + user);
    }
}