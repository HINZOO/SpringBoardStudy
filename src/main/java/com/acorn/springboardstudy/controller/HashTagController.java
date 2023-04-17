package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.HashTagDto;
import com.acorn.springboardstudy.service.HashTagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//@Controller : 무조건 view(html,jsp..)를 랜더하거나 redirect하는 동적페이지
//@RestController:무조건 view을 랜더하지않는 컨트롤러, 무조건 json or text 을 응답하는 동적페이지.
//                    (controller에서 @ResponseBody)

@RestController //html을 랜더하지않는 컨트롤러, 무조건 json을 응답하는 동적페이지.
@RequestMapping("/hashtag")
@AllArgsConstructor
public class HashTagController {
    private HashTagService hashTagService;
    @GetMapping("/{tag}/search.do")
    public List<HashTagDto> search(@PathVariable String tag){
        List<HashTagDto> tags=hashTagService.search(tag);
        return tags;
    }
}
