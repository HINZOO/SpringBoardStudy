package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardImgDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardImgMapperTest {
    @Autowired
    BoardImgMapper boardImgMapper;
    @Test
    void findByBId() {
        List<BoardImgDto> boardImgs=boardImgMapper.findByBId(4);
        System.out.println(boardImgs);
    }

    @Test
    void insertOneAndDeleteOne() {
        BoardImgDto boardImgDto= new BoardImgDto();
        boardImgDto.setBId(4);
        boardImgDto.setImgPath("/img/img99.jpg");
       int insert= boardImgMapper.insertOne(boardImgDto);
       int delete= boardImgMapper.deleteOne(boardImgDto.getBiId());

       assertEquals(insert+delete,2);
    }

    @Test
    void deleteOne() {

    }
}