package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardDto;
import com.acorn.springboardstudy.dto.BoardImgDto;
import com.acorn.springboardstudy.dto.BoardPageDto;
import com.acorn.springboardstudy.dto.UserDto;

import java.util.List;

public interface BoardService {
    //파일업로드
    //리스트,상세(조회수),수정,등록,삭제
    List<BoardDto> list(UserDto loginUser, BoardPageDto pageDto);
    List<BoardDto> tagList(String tag, UserDto loginUser, BoardPageDto pageDto);
    List<BoardImgDto> imgList(int[] biId);
    BoardDto detail(int bId);
    int register(BoardDto board, List<String> tags);
    int modify(BoardDto board, int[] delImgIds, List<String> tags, List<String> delTags);
    int remove(int bId);


}
