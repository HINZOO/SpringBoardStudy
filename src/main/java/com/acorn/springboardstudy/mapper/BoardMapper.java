package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper//Session Factory 에서 생성 및 관리
public interface BoardMapper {
    //리스트, 상세, 등록, 수정, 삭제 ,신고,
    List<BoardDto> findAll();
    BoardDto findByBId( int bId);
   /* BoardDto findByBId( @Param(value="b_id") int bId);=>로 지정하면 쿼리작성시 b_id로 쓸수 있다.*/
    int insertOne(BoardDto board);
    int updateOne(BoardDto board);
    int deleteOne(int bId);
    int updateStatusByBId(BoardDto boardDto);
    int updateIncrementViewCountByBId(int bId);
}
