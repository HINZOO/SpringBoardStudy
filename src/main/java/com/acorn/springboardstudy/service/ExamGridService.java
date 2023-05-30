package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ExamGridDto;
import com.acorn.springboardstudy.dto.ExamPageDto;

import java.util.List;

public interface ExamGridService {
    List<ExamGridDto> list(ExamPageDto pageDto);
    ExamGridDto detail(int eId);
    int register(ExamGridDto examGridDto);
    int remove(int eId);
}
