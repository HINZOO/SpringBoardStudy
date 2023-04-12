package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardHashTagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BoardHashMapper {
    //게시글+태그리스트 //태그검색시 태그리스트+태그수 //태그 등록 //태그 삭제
    List<BoardHashTagDto> findByBId(int bId);
    int countByTag(String tag);
    int insertOne(BoardHashTagDto boardHashTag);
    int deleteOne(BoardHashTagDto boardHashTag);


}
