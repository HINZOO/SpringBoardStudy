package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.HashTagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface HashTagMapper {
    //태그검색 //해시태그조회(등록전태그가있는지체크) //새로운 태그 등록 //삭제,수정X
    List<HashTagDto> findByTagContains(String tag); //where tag like '홍%'-> 홍대입구, 홍대맛집.. ->컨테이닝이라 부른다.
    HashTagDto findByTag(String tag);
    int insertOne(String tag);//tagDto의 키가 하나라 그냥 string으로 넣음..😎
}
