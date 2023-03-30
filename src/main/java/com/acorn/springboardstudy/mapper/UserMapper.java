package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //로그인, 개인정보조회, 수정, 삭제, 회원가입, 아이디찾기(email,phone,이름 입력시 아이디),
    // 비밀번호 찾기(변경=>[email or 핸드폰 인증] 로 변경페이지 반환 (pwUpdate.do)=> 변경Action)
    UserDto findByUId(String uId);
    UserDto findByUIdAndPw(UserDto user);//로그인
    String findUIdByEmailAndPhoneAndName(UserDto user);//아이디 찾기
    int insertOne(UserDto user);
    int updateOne(UserDto user);
    int updatePwByUId(UserDto user);//비밀번호변경
    int deleteByUIdAndPw(UserDto user);//아이디와 비밀번호 정보를 받음. 두개이상의 정보를 받을때는 객체오 받는것도 갠춘
}
