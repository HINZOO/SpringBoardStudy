package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ChatRoomDto;
import com.acorn.springboardstudy.mapper.ChatRoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private ChatRoomMapper chatRoomMapper;
    @Override
    public List<ChatRoomDto> list() {
        List<ChatRoomDto> list=chatRoomMapper.findAll();
        return list;
    }

    @Override
    public int register(ChatRoomDto chatRoomDto) {
        return 0;
    }

    @Override
    public int modify(ChatRoomDto chatRoomDto) {
        return 0;
    }

    @Override
    public int remove(int crId) {
        return 0;
    }
}
