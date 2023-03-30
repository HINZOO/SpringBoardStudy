package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardLikeDto;
import com.acorn.springboardstudy.dto.LikeStatusCntDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardLikeMapperTest {
    @Autowired
    private BoardLikeMapper boardLikeMapper;


    @Test
    void countStatusByBId() {
        LikeStatusCntDto likeStatusCnt=boardLikeMapper.countStatusByBId(10);
        Assertions.assertNotNull(likeStatusCnt);
    }

    @Test
    void countStatusByUId() {
        LikeStatusCntDto likeStatusCnt=boardLikeMapper.countStatusByUId("user01");
        Assertions.assertNotNull(likeStatusCnt);
    }

    @Test
    void insertUpdateDeleteOne() {
        BoardLikeDto boardLike =new BoardLikeDto();
        boardLike.setBId(10);
        boardLike.setUId("user12");
        boardLike.setStatus("BEST");
        int insert=boardLikeMapper.insertOne(boardLike);
        boardLike.setStatus("BAD");
        int update= boardLikeMapper.updateOne(boardLike);
        int delete= boardLikeMapper.deleteOne(boardLike);
        Assertions.assertEquals(insert+update+delete,3);
    }


}