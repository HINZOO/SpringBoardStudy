package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardHashTagDto;
import com.acorn.springboardstudy.dto.HashTagDto;
import com.acorn.springboardstudy.mapper.BoardHashMapper;
import com.acorn.springboardstudy.mapper.HashTagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class HashTagServiceImpl implements HashTagService{
    private HashTagMapper hashTagMapper;
    private BoardHashMapper boardHashMapper;

    @Override
    public List<HashTagDto> search(String tag) {
        List<HashTagDto> search=hashTagMapper.findByTagContains(tag);
        return search;
    }

    @Override
    public int register(BoardHashTagDto boardHashTag) {
        return 0;
    }

    @Override
    public int remove(BoardHashTagDto boardHashTag) {
        return 0;
    }
}
