package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardLikeDto;
import com.acorn.springboardstudy.dto.LikeStatusCntDto;

public interface BoardLikeService {
    //해당 게시글의 좋아요 내역을 집계한 결과(LikeStatusCntDto)조회
    //좋아요 등록 | 수정 | 삭제
    //로그인한 유저가 좋아요를 했는지 확인
    LikeStatusCntDto read(int bId);
    LikeStatusCntDto read(int bId,String loginUserId);
    BoardLikeDto detail(int bId,String uId);
    int register(BoardLikeDto like);
    int modify(BoardLikeDto like);
    int remove(BoardLikeDto like);//uId,bId 필요


}
