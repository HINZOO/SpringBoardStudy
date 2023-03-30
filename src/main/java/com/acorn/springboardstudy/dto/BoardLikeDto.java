package com.acorn.springboardstudy.dto;

import lombok.Data;

//table board
@Data
public class BoardLikeDto {
    //uk(b_id,u_id) : 유저가 게시글에 좋아요를 한번만 하도록 하기위해
    private int biId;//pk(Generate Key)
    private int bId;//fk boards.b_id
    private String uId;//fk users.u_id
    private String status;//['LIKE','BAD','SAD','BEST']



}
