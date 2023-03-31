package com.acorn.springboardstudy.dto;

import lombok.Data;

//table reply_likes
@Data
public class ReplyLikeDto {
    //uk(br_id,u_id) : 유저가 댓글에 좋아요를 한번만 하도록 하기위해
    private int rlId;//pk(Generate Key)
    private int brId;//fk boards_reply.b_id
    private String uId;//fk users.u_id
    private String status;//['LIKE','BAD','SAD','BEST']



}
