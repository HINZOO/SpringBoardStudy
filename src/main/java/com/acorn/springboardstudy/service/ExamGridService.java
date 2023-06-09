package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ExamGridDto;
import com.acorn.springboardstudy.dto.ExamPageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ExamGridService {
    List<ExamGridDto> list(ExamGridDto examGridDto);
    ExamGridDto detail(int eId);
    ExamGridDto idCheck(String uId);
    int register(ExamGridDto examGridDto);
    int modify(ExamGridDto examGridDto);
    int remove(int eId);
}
