package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardReplyDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//TTD(단위테스트개발)
@SpringBootTest //junit 테스트를 할 때, 스프링부트가 같이 실행되면서 의존성 주입을 받을 수 있다.
class BoardReplyMapperTest {
    @BeforeAll
    static void init(){
    //테스트 시작 전 사전설정~
    }
    @Autowired
    BoardReplyMapper boardReplyMapper;
    @Test
    void findByBIdAndParentBrIdIsNull() {
        List<BoardReplyDto> boardReplies = boardReplyMapper.findByBIdAndParentBrIdIsNull(16);
        System.out.println("boardReplies = " + boardReplies);

       assertNotNull(boardReplies);
    }

    @Test
    void findByParentBrId() {
        List<BoardReplyDto> replies= boardReplyMapper.findByParentBrId(16);
        System.out.println("replies = " + replies);//println도 호출에 해당한다.따라서 lazy가 실행
        assertNotNull(replies);

    }

    @Test
    void findByBrId() {
        BoardReplyDto boardReply=boardReplyMapper.findByBrId(16);
        System.out.println("boardReply = " + boardReply.getReplies());
        assertNotNull(boardReply);
    }

    @Test
    void insertOne() {//대댓글 등록 체크 //댓글도 달수 있음~
        BoardReplyDto boardReply=new BoardReplyDto();
        boardReply.setBId(6);
        boardReply.setParentBrId(24);//대대댓글
        boardReply.setUId("user05");
        boardReply.setImgPath("테스트댓글이미지.jpg");
        boardReply.setContent("대대댓글 테스트입니다!");

        int insert=boardReplyMapper.insertOne(boardReply);
       assertEquals(insert,1);
    }

    @Test
    void InsertUpdateDeleteOne() {
        BoardReplyDto boardReply=new BoardReplyDto();
        boardReply.setBId(6);
        boardReply.setParentBrId(19);
        boardReply.setUId("user07");
        boardReply.setContent("19번 댓글에 대한 대댓글 입력테스트");
        boardReply.setImgPath("대댓글이미지테스트");
        int insert=boardReplyMapper.insertOne(boardReply);
        //boardReply.setBrId(boardReply.getBrId());//resultMap에서 useGeneratedKeys="true" keyProperty="brId" 설정을 해줘서 brId입력이 필요없음
        boardReply.setContent("19번 댓글에 대한 대댓글 수정테스트");
        boardReply.setImgPath("수정테스트");
        int update= boardReplyMapper.updateOne(boardReply);
        int delete= boardReplyMapper.deleteOne(boardReply.getBrId());
        assertEquals(insert+update+delete,3);

    }

    @Test
    void deleteOne() {
        int delete= boardReplyMapper.deleteOne(20);
       assertEquals(delete,1);
    }
}