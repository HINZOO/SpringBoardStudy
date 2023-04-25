package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ChatMsgDto;
import com.acorn.springboardstudy.mapper.ChatMsgMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ChatMsgServiceImpl implements ChatMsgService {
    private ChatMsgMapper chatMsgMapper;
    @Override
    public int register(ChatMsgDto chatMsgDto) {
        int register=chatMsgMapper.insertOne(chatMsgDto);
        return register;
    }

    @Override
    public List<ChatMsgDto> list(int crId) {
        List<ChatMsgDto> list=chatMsgMapper.findByCrId(crId);
        return list;
    }

    @Override
    public List<ChatMsgDto> list(int crId, String postTime) {
        List<ChatMsgDto> list=chatMsgMapper.findByCrIdAndGraterThanPostTime(crId,postTime);
        return list;
    }
}
