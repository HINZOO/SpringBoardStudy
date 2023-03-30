package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardImgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardImgMapper {
    //BoardImgMapper.xml 만들고 BoardImgMapper.xml 에서 collection 정의로 imgs 불러오기
    //BoardImgMapperTest 생성

    //게시글에서 조회되는 이미지 리스트
    //게시글에 이미지 등록
    //게시글의 이미지 삭제(수정없음)
    List<BoardImgDto> findByBId(int bId);
    int insertOne(BoardImgDto boardImgDto);
    int deleteOne(int biId);
}
