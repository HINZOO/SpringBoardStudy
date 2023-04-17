package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.*;
import com.acorn.springboardstudy.mapper.*;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    private BoardMapper boardMapper;
    private UserMapper userMapper;
    private BoardImgMapper boardImgMapper;
    private HashTagMapper hashTagMapper;
    private BoardHashMapper boardHashMapper;

    @Override
    public List<BoardDto> list(UserDto loginUser) {
        if(loginUser!=null)userMapper.setLoginUserId(loginUser.getUId()); //로그인한 유저아이디를 mysql 서버의 변수로 등록
        PageHelper.startPage(1,5,"b_id");//현재페이지/오프셋/orderby
        List<BoardDto> list=boardMapper.findAll();//그 변수로 지연로딩으로 좋아요 불러오기
        if(loginUser!=null)userMapper.setLoginUserIdNull();//사용이 끝나서 삭제;
        return list;
    }

    @Override
    public List<BoardDto> tagList(String tag, UserDto loginUser) {
        if(loginUser!=null) userMapper.setLoginUserId(loginUser.getUId());//로그인한 유저가 좋아요한 내역불러오기
        List<BoardDto> tagList=boardMapper.findByTag(tag);
        if(loginUser!=null) userMapper.setLoginUserIdNull();//로그인한 유저가 좋아요 한 내역 지우기
        return tagList;
    }

    @Override
    public List<BoardImgDto> imgList(int[] biId) {
        List<BoardImgDto> imgList = null;
        if(biId!=null){
            imgList=new ArrayList<>();
            for(int iId : biId){
                BoardImgDto imgDto=boardImgMapper.findByBiId(iId);
                imgList.add(imgDto);
            }
        }
        return imgList;
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
    public int register(BoardDto board, List<String> tags) {
        int register=0;
        register=boardMapper.insertOne(board);//bId생성(useGeneratedKeys="true" keyProperty="bId")
        if(board.getImgs()!=null){
            for(BoardImgDto img:board.getImgs()){
                img.setBId(board.getBId());//생성된 bId를 집어넣음
                register+=boardImgMapper.insertOne(img);
            }
        }
        if(tags!=null){
            for(String tag:tags){
                HashTagDto hashTagDto=hashTagMapper.findByTag(tag);
                if(hashTagDto==null) register+=hashTagMapper.insertOne(tag);
                BoardHashTagDto boardHashTag=new BoardHashTagDto();
                boardHashTag.setBId(board.getBId());
                boardHashTag.setTag(tag);
                register+=boardHashMapper.insertOne(boardHashTag);
            }
        }
        return register;
    }

    @Override
    @Transactional
    public int modify(BoardDto board, int[] delImgIds, List<String> tags, List<String> delTags) {
        int modify=boardMapper.updateOne(board);

        if(delImgIds!=null){
            for(int biId:delImgIds){
                modify+=boardImgMapper.deleteOne(biId);//서버 상 지움
            }
        }
        if(tags!=null){
            for(String tag:tags){
                HashTagDto hashTagDto=hashTagMapper.findByTag(tag);
                if(hashTagDto==null) modify+=hashTagMapper.insertOne(tag);
                BoardHashTagDto boardHashTag=new BoardHashTagDto();
                boardHashTag.setBId(board.getBId());
                boardHashTag.setTag(tag);
                modify+=boardHashMapper.insertOne(boardHashTag);
            }
        }
        if(delTags!=null){
            for(String tag:delTags){
                BoardHashTagDto boardHashTag=new BoardHashTagDto();
                boardHashTag.setBId(board.getBId());
                boardHashTag.setTag(tag);
                modify+=boardHashMapper.deleteOne(boardHashTag);
            }
        }
        return modify;
        //int 대신에 boardImg 리스트를 반환..(맵으로 반환해서 인트값과 보드이미지를 반환하는것도 방법이겠다..)
    }

    @Override
    public int remove(int bId) {
        int remove=boardMapper.deleteOne(bId);
        return remove;
    }
}
