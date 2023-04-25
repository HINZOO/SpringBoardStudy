package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.ChatMsgDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChatMsgMapperTest {
@Autowired
ChatMsgMapper chatMsgMapper;
    @Test
    void insertOne() {
        ChatMsgDto chatMsgDto=new ChatMsgDto();
        chatMsgDto.setCrId(1);
        chatMsgDto.setNickName("나야");
        chatMsgDto.setUId("user05");
        chatMsgDto.setContent("밖에 비와?");
        chatMsgDto.setStatus(ChatMsgDto.Status.ENTER);
        chatMsgMapper.insertOne(chatMsgDto);
    }

    @Test
    void findByCrId() {
        chatMsgMapper.findByCrId(1);
    }

    @Test
    void findByCrIdAndGraterThanPostTime() {
        chatMsgMapper.findByCrIdAndGraterThanPostTime(1,"2023-04-25 15:25:00");
    }
}