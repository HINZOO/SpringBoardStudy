package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.ChatMsgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ChatMsgMapper {
    //메세지를 보낸다(insert)
    //룸에서 메세지 리스트를 받는다 (select)
    //가장 최근에 받은 메세지의 다음 메세지 리스트를 조회
    int insertOne(ChatMsgDto chatMsgDto);
    List<ChatMsgDto> findByCrId(int crId);
    List<ChatMsgDto> findByCrIdAndGraterThanPostTime(@Param("crId") int crId, @Param("postTime") String postTime);
    //매개변수가 2개 이상이면 파라미터를 받아오지 못하는 경우가 많아 param 어노테이션을 쓴다. (테스트를 통해 받아올수있는지 확인가능)
}
