package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.FollowDto;
import com.acorn.springboardstudy.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    //1.로그인한 유저가 구독하는 유저들 (팔로우)
    //2.로그인한 유저를 구독하는 유저들 (팔로워)
    //3.로그인한 유저를 구독하는 사람을 삭제 팔로워삭제
    //4.로그인한 유저가 자신의 구독리스트인 사람을 삭제 팔로우 삭제
    //5.구독신청
    List<UserDto> findByFromId(String uId); //1.내가 구독하는 사람(팔로우)
    List<UserDto> findByToId(String uId); //2.나를 구독하는 사람(팔로워)
    int deleteByFromIdAndToId(FollowDto followDto);//3,4
    int insertOne(FollowDto followDto);//5.

}
