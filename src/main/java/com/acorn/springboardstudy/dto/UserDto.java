package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class UserDto {

    
    private String uId;
    private String pw;
    private String name;
    private String phone;
    private String imgPath;
    private String email;
    private Date postTime;
    private String birth;
    private String gender;
    private String address;
    private String detailAddress;
    private String permission;
    //POJO(get/set) 약속에서 boolean타입은 get이 아니라 is 여서 변수에 is를 앞에 두면 에러 생김..
    private boolean following; //로그인한 유저가 해당 유저를 팔로잉 중인가?
    private List<UserDto> followings;//팔로우 리스트 users:follows = 1: N (from_id=u_id)
    private List<UserDto> followers; //팔로워 리스트 users:follows = 1: N (to_id=u_id)

}