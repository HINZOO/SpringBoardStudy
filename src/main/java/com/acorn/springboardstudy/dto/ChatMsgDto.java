package com.acorn.springboardstudy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class ChatMsgDto {
    public enum Status{
        ENTER,LEAVE,CHAT
    }
    @JsonProperty("cmId")
    private int cmId;
    @JsonProperty("crId")
    private int crId;
    @JsonProperty("uId")
    private String uId;
    private String nickName;//별칭
    private String content;//메세지내용
    private Status status;//메세지상태
    @JsonFormat(timezone ="Asia/Seoul",pattern = "yyyy-MM-dd HH:mm:ss")//json이 응답할떄 Date를 미국시간대로 설정하는 경우가 있어서 이를 방지.
    @JsonProperty("postTime")
    private Date postTime;//등록시간

}
