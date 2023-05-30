package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.*;
import com.acorn.springboardstudy.service.ExamGridService;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/examgrid")
@Log4j2
public class ExamController {

    private ExamGridService examGridService;

    public ExamController(ExamGridService examGridService) {
        this.examGridService = examGridService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @ModelAttribute ExamPageDto pageDto){
        List<ExamGridDto> examGridDtos;
        examGridDtos=examGridService.list(pageDto);
        PageInfo<ExamGridDto> pageExam=new PageInfo<>(examGridDtos);
        model.addAttribute("exam",examGridDtos);
        model.addAttribute("page",pageExam);
        return "/examgrid/list";
    }



    @GetMapping("/register.do")
    public void registerForm(){
    }

    @PostMapping("/register.do")
    public String registerAction(@ModelAttribute ExamGridDto examGridDto){
        int insert;
        insert=examGridService.register(examGridDto);
        return "redirect:/examgrid/list.do";
    }


    @PostMapping("/remove.do")
    public String deleteAction(@RequestParam("selectedIds") List<String> selectedIds){
        int remove;
        for(String id:selectedIds){
            int eId=Integer.parseInt(id);
            remove=examGridService.remove(eId);
        }
        return "redirect:/examgrid/list.do";
    }


}
