package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ChatRoomDto {
    private int crId; //pk auto_increment
    private String uId;//fk user u_id
    private String name; //채팅방이름
    private String description; //채팅방설명
    private Date postTime;
    private Date updateTime;

}
