package com.acorn.springboardstudy.mapper;

import com.acorn.springboardstudy.dto.ExamGridDto;
import com.acorn.springboardstudy.dto.ExamPageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamGridMapper {
    //리스트조회 //저장 //삭제
    List<ExamGridDto> findAll(ExamPageDto pageDto);
    ExamGridDto findByEId(int eId);
    int insertOne(ExamGridDto examGridDto);
    int updateOne(ExamGridDto examGridDto);
    int deleteOne(int eId);
}
