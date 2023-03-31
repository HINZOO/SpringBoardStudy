package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.LikeStatusCntDto;
import com.acorn.springboardstudy.dto.ReplyLikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyLikeMapper {
    //댓글의 좋아요 싫어요 최고예요 슬퍼요 수를 반환
    //유저가 댓글에 작성한 좋아요 싫어요 최고예요 나빠요 수를 반환
    //댓글에서 유저가 좋아요 싫어요 최고예요 나빠요를 등록
    //댓글(brId)에서 로그인한 유저(uId)가 좋아요를 했는지 확인.
    //댓글에서 유저가 좋아요 를 했다면 싫어요 최고예요 나빠요 로 수정
    //댓글에서 유저가 좋아요 를 했다면 좋아요를 취소 (삭제)

    ReplyLikeDto findByuIdAndBrId(String uId, int brId);//한유저가 한댓글에 남긴 좋아요기록.

    LikeStatusCntDto countStatusByBrId(int brId);
    LikeStatusCntDto countStatusByUId(String uId);

    int insertOne(ReplyLikeDto replyLike);
    int updateOne(ReplyLikeDto replyLike);
    int deleteOne(ReplyLikeDto replyLike);


}
