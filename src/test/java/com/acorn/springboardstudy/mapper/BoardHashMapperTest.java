package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardHashTagDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardHashMapperTest {
    @Autowired
    private BoardHashMapper boardHashMapper;

    static BoardHashTagDto boardHashTag;
    @Test
    @Order(1)
    void insertOne() {
        boardHashTag= new BoardHashTagDto();
        boardHashTag.setBId(1);
        boardHashTag.setTag("에이콘아카데미");
        int insert=boardHashMapper.insertOne(boardHashTag);
        assertTrue(insert>0);
    }
    @Test
    @Order(2)
    void findByBId() {
        List<BoardHashTagDto> tags=boardHashMapper.findByBId(1);
        assertNotNull(tags);
    }

    @Test
    @Order(3)
    void countByTag() {
        int cnt=boardHashMapper.countByTag("홍대맛집");
        assertTrue(cnt>0);
    }
    @Test
    @Order(4)
    void deleteOne() {
        int delete= boardHashMapper.deleteOne(boardHashTag);
        assertTrue(delete>0);
    }


}