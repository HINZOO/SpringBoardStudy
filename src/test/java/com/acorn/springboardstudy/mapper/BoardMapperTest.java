package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//Junit의 단위테스트는 spring 과 별개로 실행.따라서 객체주입을 받을 수 없는데
// springBoot 3.0v 부터 @SpringBootTest 을 정의하면 spring 에서 생성하는 객체를 주입받을 수 있다.
class BoardMapperTest {
    //Junit은 클래스의 생성자를 정의할 수 없다. 따라서 생성자 없이 객체를 주입받는 @Autowired를 작성해야한다.
    @Autowired
    private BoardMapper boardMapper;

    @Test
    void findAll() {
        List<BoardDto> boardList=boardMapper.findAll();
        System.out.println("boardList = " + boardList);
    }

    @Test
    void findByBId() {
        BoardDto board=boardMapper.findByBId(1);
        //지연로딩(ResultMap.Collection.fetchType=lazy) : 호출할 때(트리거(=get,toString)) 그 순간에 조회
        //일반적으로 board를 조회할때는 replies가 출력되지 않고  getReplies()가 있을때 즉 호출할때 replies를  조회
//        System.out.println("board.getReplies() = " + board.getReplies());
//        System.out.println("board.getUser() = " + board.getUser());
//        System.out.println("board.getImgs() = " + board.getImgs());
//        System.out.println("board.getlikes() = " + board.getLikes());
        System.out.println("board.getTags() = " + board.getTags());
        assertNotNull(board);


    }

    @Test
    void insertOneAndDeleteOne() {
        BoardDto board=new BoardDto();
        board.setUId("user09");
        board.setTitle("test00의 글2");
        board.setContent("블라블라2");
        int insert= boardMapper.insertOne(board);
        System.out.println("입력성공:"+insert);
        System.out.println(board);
        int delete=boardMapper.deleteOne(board.getBId());
        System.out.println("delete = " + delete);
        assertEquals(insert+delete,2);
    }

    @Test
    void updateOne() {
        BoardDto board=new BoardDto();
        board.setBId(36);
        board.setTitle("수정테스트");
        board.setContent("수정글테스트입니다.");
        int update= boardMapper.updateOne(board);
        assertEquals(update,1);//수정 성공 확인 성공시 1 반환
        BoardDto updateBoard=boardMapper.findByBId(36);
        System.out.println("updateBoard = " + updateBoard);
    }

    @Test
    void deleteOne() {
        int delete= boardMapper.deleteOne(35);
        System.out.println("삭제성공:"+delete);
    }

    @Test
    void updateStatusByBId() {
        BoardDto board=new BoardDto();
        board.setBId(36);
        board.setStatus("REPORT");
        int update= boardMapper.updateStatusByBId(board);
        assertEquals(update,1);//수정 성공 확인 성공시 1 반환
        BoardDto updateBoard=boardMapper.findByBId(36);
        System.out.println("updateBoard = " + updateBoard);
    }

    @Test
    void updateIncrementViewCountByBId() {
        int updateIncrementViewCountByBId = boardMapper.updateIncrementViewCountByBId(1);
        assertEquals(updateIncrementViewCountByBId,1);
    }

    @Test
    void findByTag() {
        List<BoardDto> boards=boardMapper.findByTag("홍대");
        assertNotNull(boards);
    }
}
//view 하기 전에 user로 조인 board_like 도 join 해서 테스트 진행~~~