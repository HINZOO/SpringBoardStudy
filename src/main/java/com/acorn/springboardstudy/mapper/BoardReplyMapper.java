package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardReplyMapper {
    //게시글에 댓글리스트  (ParentBrId is Null)
    //대댓글 리스트🤣
    //댓글 수정 폼 출력시 댓글 상세
    //댓글 등록
    //댓글 수정
    //댓글 삭제
    List<BoardReplyDto> findByBIdAndParentBrIdIsNull(int bId);
    List<BoardReplyDto> findByParentBrId(int brId);//br_id===parent_br_id
    BoardReplyDto findByBrId(int brId);
    int insertOne(BoardReplyDto boardReply);
    int updateOne(BoardReplyDto boardReply);
    int deleteOne(int brId);

}
