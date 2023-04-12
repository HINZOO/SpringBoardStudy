package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardDto;
import com.acorn.springboardstudy.dto.BoardImgDto;

import java.util.List;

public interface BoardService {
    //파일업로드
    //리스트,상세(조회수),수정,등록,삭제
    List<BoardDto> list();
    List<BoardDto> list(String loginUserId);
    List<BoardImgDto> imgList(int[] biId);
    BoardDto detail(int bId);
    int register(BoardDto board);
    int modify(BoardDto board, int[] delImgIds);
    int remove(int bId);


}
