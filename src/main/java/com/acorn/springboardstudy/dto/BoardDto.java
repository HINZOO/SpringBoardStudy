package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BoardDto {
    private int bId;//pk
    private String uId;//fk user.u_id
    private Date postTime; //default CURRENT_TIMESTAMP
    private Date updateTime; //default on update CURRENT_TIMESTAMP
    private String status; // enum [PUBLIC, PRIVATE, REPORT, BLOCK]
    private String title;
    private String content;
    private int viewCount;

    private LikeStatusCntDto likes; //1:N = boards:board_likes 이지만 집계한 결과만 조회
    private UserDto user;//N:1 = boards : user
    private List<BoardReplyDto> replies; //1:N관계 = board :board_replies
    private List<BoardImgDto> imgs; //1:N 관계 = board :board_imgs


}
