package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//Table Name : board_replies
public class BoardReplyDto {
    private int brId;//pk generate key(== AutoIncrement)
    private int bId; //fk boards.b_id
    private String uId; //fk users.u_id
    private Integer parentBrId;//self join fk board_replies.br_id ISNULL
    private Date postTime;//CURRENT_TIMESTAMP
    private Date updateTime; //CURRENT_TIMESTAMP on update
    private String status;//[PUBLIC,PRIVATE,REPORT,BLOCK]
    private String imgPath;
    private String content;
    private List<BoardReplyDto> replies;//대댓글(selfJoin:리스트로 나자신의 타입을 참조)
    //board_replies:board_replies=1:N (self join)
}
