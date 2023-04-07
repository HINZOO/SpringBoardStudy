package com.acorn.springboardstudy.service;

import com.acorn.springboardstudy.dto.BoardReplyDto;
import com.acorn.springboardstudy.mapper.BoardReplyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoardReplyServiceImpl implements BoardReplyService {
    private BoardReplyMapper boardReplyMapper;
    @Override
    public List<BoardReplyDto> list(int bId) {
        List<BoardReplyDto> list=boardReplyMapper.findByBIdAndParentBrIdIsNull(bId);
        return list;
    }


    @Override
    public BoardReplyDto detail(int brId) {
        BoardReplyDto detail=boardReplyMapper.findByBrId(brId);
        return detail;
    }

    @Override
    public int register(BoardReplyDto boardReply) {
        int register=boardReplyMapper.insertOne(boardReply);
        return register;
    }

    @Override
    public int modify(BoardReplyDto boardReply) {
        int modify=boardReplyMapper.updateOne(boardReply);
        return modify;
    }

    @Override
    public int remove(int brId) {
        int remove=boardReplyMapper.deleteOne(brId);
        return remove;
    }
}
