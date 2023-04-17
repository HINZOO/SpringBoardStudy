package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardHashTagDto;
import com.acorn.springboardstudy.dto.HashTagDto;

import java.util.List;

public interface HashTagService {
    //검색 //게시글에 해시태그를 등록 (만약 해시태그가 없다면 해시태그도 등록) //게시글을 수정할 때 해시태그 삭제
    List<HashTagDto> search(String tag);
    int register(BoardHashTagDto boardHashTag);//보드 해시태그 서비스를 따로 만들지 않고 여기서 진행
    int remove(BoardHashTagDto boardHashTag);


}
