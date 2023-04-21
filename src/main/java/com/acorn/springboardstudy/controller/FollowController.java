package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.FollowDto;
import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {
    private FollowService followService;
    @PostMapping("/{uId}/{follower}/handler.do")
    public int registerHandler(@PathVariable String uId,
                               @PathVariable boolean follower,
                               @SessionAttribute UserDto loginUser){//로그인안 한 유저가 들어오면 400
        int register=0;
        if(loginUser.getUId().equals(uId)) return register;//로그인한 유저가 자기 자신을 팔로우 못하도록 설정
        FollowDto followDto=new FollowDto();
        if(follower){//팔로워 등록
            followDto.setToId(loginUser.getUId());
            followDto.setFromId(uId);
        }else{//팔로우등록
            followDto.setToId(uId);
            followDto.setFromId(loginUser.getUId());
        }
        register= followService.register(followDto);//500 (등록실패)
        return register;
    }

    //구독했던 사람을 지우는 경우 //follower=true
    //나를 구독한 사람을 지우는 경우 //follower=false
    @DeleteMapping("/{uId}/{follower}/handler.do")
    public int removeHandler(@PathVariable String uId,
                             @PathVariable boolean follower,
                             @SessionAttribute UserDto loginUser){
        int remove=0;
        FollowDto followDto=new FollowDto();
        if(follower){
            followDto.setFromId(uId);
            followDto.setToId(loginUser.getUId());
        }else{//?
            followDto.setFromId(loginUser.getUId());
            followDto.setToId(uId);
        }
        remove= followService.remove(followDto);
        return remove;
    }
}
