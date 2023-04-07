package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardDto;
import com.acorn.springboardstudy.mapper.BoardMapper;
import com.acorn.springboardstudy.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    private BoardMapper boardMapper;
    private UserMapper userMapper;
    @Override
    public List<BoardDto> list() {
        List<BoardDto> list=boardMapper.findAll();
        return list;
    }

    @Override
    public List<BoardDto> list(String loginUserId) {
        //List<BoardDto> list=boardMapper.findAll(loginUserId);//서브쿼리로 좋아요 불러오기
        userMapper.setLoginUserId(loginUserId); //로그인한 유저아이디를 mysql 서버의 변수로 등록
        List<BoardDto> list=boardMapper.findAll();//그 변수로 지연로딩으로 좋아요 불러오기
        userMapper.setLoginUserIdNull();//사용이 끝나서 삭제
        return list;
    }

    @Override
    @Transactional
    public BoardDto detail(int bId) {
        //dataSource.getConnection().commit()
        boardMapper.updateIncrementViewCountByBId(bId);//조회수올리기
        BoardDto detail=boardMapper.findByBId(bId);
        //예외가 뜨면, dataSource.getConnection().commit().rollBack()
        return detail;
    }

    @Override
    public int register(BoardDto board) {
        int register=boardMapper.insertOne(board);
        return register;
    }

    @Override
    public int modify(BoardDto board) {
        int modify=boardMapper.updateOne(board);
        return modify;
    }

    @Override
    public int remove(int bId) {
        int remove=boardMapper.deleteOne(bId);
        return remove;
    }
}
