package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardReplyDto;

import java.util.List;

public interface BoardReplyService {
    //등록 // 수정 // 삭제 //상세 //리스트(게시글번호의)
    List<BoardReplyDto> list(int bId);
    BoardReplyDto detail(int brId);
    int register (BoardReplyDto boardReply);
    int modify(BoardReplyDto boardReply);
    int remove(int brId);


}
