package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //로그인, 개인정보조회, 수정, 삭제, 등록
    UserDto findByUId(String uId);

}
