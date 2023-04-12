package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardImgDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;
    @Test
    void imgList() {
        List<BoardImgDto> boardImgDtoList=boardService.imgList(new int[] {34,35,36});
        Assertions.assertNotNull(boardImgDtoList);
        System.out.println("boardImgDtoList = " + boardImgDtoList);
    }
}