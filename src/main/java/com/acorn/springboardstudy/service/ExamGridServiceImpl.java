package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.ExamGridDto;
import com.acorn.springboardstudy.dto.ExamPageDto;
import com.acorn.springboardstudy.mapper.ExamGridMapper;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ExamGridServiceImpl implements ExamGridService{

    private ExamGridMapper examGridMapper;

    @Override
    public List<ExamGridDto> list(ExamGridDto examGridDto) {
        List<ExamGridDto>list = examGridMapper.findAll(examGridDto);
        return list;
    }

    @Override
    public ExamGridDto detail(int eId) {
        ExamGridDto detail=examGridMapper.findByEId(eId);
        return detail;
    }

    @Override
    public ExamGridDto idCheck(String uId) {
        ExamGridDto idCheck=examGridMapper.findByUId(uId);
        return idCheck;
    }

    @Override
    public int register(ExamGridDto examGridDto) {
        int register;
        register=examGridMapper.insertOne(examGridDto);
        return register;
    }

    @Override
    public int modify(ExamGridDto examGridDto) {
        int modify;
        modify=examGridMapper.updateOne(examGridDto);
        return modify;
    }

    @Override
    public int remove(int eId) {
        int remove;
        remove=examGridMapper.deleteOne(eId);
        return remove;
    }


}
