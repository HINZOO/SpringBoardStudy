package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardDto;
import com.acorn.springboardstudy.dto.BoardImgDto;
import com.acorn.springboardstudy.mapper.BoardImgMapper;
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
    private BoardImgMapper boardImgMapper;
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
    @Transactional//중간에 실수하면 등록취소
    public int register(BoardDto board) {
        int register=0;
        register=boardMapper.insertOne(board);//bId생성(useGeneratedKeys="true" keyProperty="bId")
        if(board.getImgs()!=null){
            for(BoardImgDto img:board.getImgs()){
                img.setBId(board.getBId());//생성된 bId를 집어넣음
                register+=boardImgMapper.insertOne(img);
            }
        }
        return register;
    }

    @Override
    @Transactional
    public int modify(BoardDto board, int[] delImgIds) {
        int modify=boardMapper.updateOne(board);
        if(delImgIds!=null){
            for(int biId:delImgIds){
                modify+=boardImgMapper.deleteOne(biId);
            }
        }
        return modify;
    }

    @Override
    public int remove(int bId) {
        int remove=boardMapper.deleteOne(bId);
        return remove;
    }
}
