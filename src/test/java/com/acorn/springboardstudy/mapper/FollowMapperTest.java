package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.FollowDto;
import com.acorn.springboardstudy.dto.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FollowMapperTest {
    @Autowired
    private FollowMapper followMapper;
    @Test
    @Order(1)
    void insertOne() {
        FollowDto followDto = new FollowDto();
        followDto.setFromId("user01");
        followDto.setToId("user17");
        //user01이 user17을 구독
        int insert= followMapper.insertOne(followDto);
        assertEquals(insert,1);
    }
    @Test
    @Order(2)
    void findByFromId() {
        List<UserDto> followings=followMapper.findByFromId("user01");
        assertNotNull(followings);
    }

    @Test
    @Order(3)
    void findByToId() {
        List<UserDto> followers=followMapper.findByToId("user01");
        assertNotNull(followers);
    }

    @Test
    @Order(4)
    void deleteByFromIdAndToId() {
        FollowDto followDto=new FollowDto();
        followDto.setFromId("user01");
        followDto.setToId("user17");
        int delete= followMapper.deleteByFromIdAndToId(followDto);
        assertEquals(delete,1);
    }

}