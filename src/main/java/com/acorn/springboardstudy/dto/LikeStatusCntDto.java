package com.acorn.springboardstudy.dto;

import lombok.Data;
//->board_likes, reply_like 도 사용중
@Data
public class LikeStatusCntDto {

    //Status의 집계(count) 결과
    private int like;
    private int sad;
    private int bad;
    private int best;
    private String status; //로그인한사람이 누른 좋아요 내역
    private int id; //bId 또는 brId
}