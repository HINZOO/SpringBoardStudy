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
    private List<UserDto> followings;//팔로우 리스트 users:follows = 1: N (from_id=u_id)
    private List<UserDto> followers; //팔로워 리스트 users:follows = 1: N (to_id=u_id)

}