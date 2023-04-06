package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.BoardDto;
import com.acorn.springboardstudy.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
    private BoardService boardService; //DIP

    @GetMapping("/list.do")
    public String list(Model model){
        List<BoardDto> boards=boardService.list();
        model.addAttribute("boards",boards);
        return "/board/list";
    }
    //?bId=1 //bId 동적페이지에 꼭 필요(400)을 명시적으로 나타내는것
    @GetMapping("/{bId}/detail.do")
    public String detail(Model model,
                         @PathVariable int bId){
        BoardDto board=boardService.detail(bId);
        model.addAttribute("b",board);
        return "/board/detail";//html
    }

}
