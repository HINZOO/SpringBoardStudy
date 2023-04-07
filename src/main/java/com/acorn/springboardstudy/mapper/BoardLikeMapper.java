package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.BoardLikeDto;
import com.acorn.springboardstudy.dto.LikeStatusCntDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardLikeMapper {
    //게시글의 좋아요 싫어요 최고예요 슬퍼요 수를 반환
    //유저가 게시글 작성한 좋아요 싫어요 최고예요 나빠요 수를 반환
    //게시글에서 유저가 좋아요 싫어요 최고예요 나빠요를 등록
    //게시글에 로그인한 유저가 좋아요를 했는지 확인
    //게시글에서 유저가 좋아요 를 했다면 싫어요 최고예요 나빠요 로 수정
    //게시글에서 유저가 좋아요 를 했다면 좋아요를 취소 (삭제)
    BoardLikeDto findByBIdAndUId(@Param("bId")int bId, @Param("uId")String uId);
    String findByBIdAndUIdIsLoginUserId(@Param("bId")int bId);//로그인한 유저가 좋아요 한 내역

    LikeStatusCntDto countStatusByBId(int bId);
    LikeStatusCntDto countStatusByUId(String uId);
    int insertOne(BoardLikeDto boardLikeDto);
    int updateOne(BoardLikeDto boardLikeDto);
    int deleteOne(BoardLikeDto boardLikeDto);


}
