package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {
    //룸리스트//룸등록//룸수정//룸삭제
    List<ChatRoomDto> list();
    int register(ChatRoomDto chatRoomDto);
    int modify(ChatRoomDto chatRoomDto);
    int remove(int crId);

}
