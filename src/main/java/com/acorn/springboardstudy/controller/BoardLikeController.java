package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.BoardLikeDto;
import com.acorn.springboardstudy.dto.LikeStatusCntDto;
import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.service.BoardLikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board/like")
@AllArgsConstructor
@Log4j2
public class BoardLikeController {
    //http://localhost:8080/board/like/1/read.do
    private BoardLikeService boardLikeService;
    @GetMapping("/{bId}/read.do")
    public String readLikeStatusCnt(
            @PathVariable int bId,
            @SessionAttribute(required = false) UserDto loginUser,
            Model model){
        LikeStatusCntDto likes;
        if(loginUser!=null){
            likes=boardLikeService.read(bId,loginUser.getUId());
        }else{
            likes=boardLikeService.read(bId);

        }
        log.info(likes);
        model.addAttribute("likes",likes);
        return "/board/likes";//likes.html
    }
    // 1번글에 좋아요 누를때 url: /board/like/LIKE/1/handler.do
    // 3번글에 슬퍼요 누를때 url: /board/like/SAD/3/handler.do

    @Data
    class HandlerDto{
        enum HandlerType{REGISTER,MODIFY,REMOVE}
        private HandlerType handlerType;
        private String status;
        int handler;//성공1 실패0
    }
    @GetMapping("/{status}/{bId}/handler.do")
    public @ResponseBody HandlerDto handler(
            @PathVariable String status,
            @PathVariable int bId ,
            @SessionAttribute UserDto loginUser){
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setStatus(status);
        BoardLikeDto boardLike=boardLikeService.detail(bId,loginUser.getUId());
        int handler=0;
        BoardLikeDto like= new BoardLikeDto();
        like.setStatus(status);
        like.setUId(loginUser.getUId());
        like.setBId(bId);
        if(boardLike==null){
            handlerDto.setHandlerType(HandlerDto.HandlerType.REGISTER);
            handler=boardLikeService.register(like);
        }else{
            if(boardLike.getStatus().equals(status)){//이미 누른 좋아요의 같은 좋아요를 누른경우 취소
                handlerDto.setHandlerType(HandlerDto.HandlerType.REMOVE);
                handler=boardLikeService.remove(like);
            }else{//좋아요를 이미 누른상태에서 싫어요를 눌렀을때 싫어요로 변경
                handlerDto.setHandlerType(HandlerDto.HandlerType.MODIFY);
                handler=boardLikeService.modify(like);
            }
        }
        handlerDto.setHandler(handler);
        return handlerDto;
    }
}
