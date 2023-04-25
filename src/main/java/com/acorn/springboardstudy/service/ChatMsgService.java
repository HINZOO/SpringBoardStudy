package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ChatMsgDto;

import java.util.List;

public interface ChatMsgService {
//룸안의 메세지 전체를 조회 //메세지 중 마지막조회한 내역 다음의 리스트 조회
    //룸의 메세지 보내기
    int register(ChatMsgDto chatMsgDto);
    List<ChatMsgDto> list(int crId);//룸아이디가 꼭 필요함 (그 룸의 리스트 조회이기 떄문)
    List<ChatMsgDto> list(int crId,String postTime);//마지막조회한내역의 다음리스트 조회

}
