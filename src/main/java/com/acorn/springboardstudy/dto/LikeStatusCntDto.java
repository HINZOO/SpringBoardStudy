package com.acorn.springboardstudy.dto;

import lombok.Data;

@Data
public class LikeStatusCntDto {
    //Status의 집계(count) 결과
    private int like;
    private int sad;
    private int bad;
    private int best;
}