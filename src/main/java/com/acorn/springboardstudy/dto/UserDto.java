package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.Date;


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

}