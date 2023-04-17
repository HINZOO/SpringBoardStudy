package com.acorn.springboardstudy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HashTagDto {
    private String tag;
    @JsonProperty(value="bCnt") //json 프로퍼티가 옮기는 와중에 대소문자 구문오류가생겨서 적어줌.
    private int bCnt; //fetch.LAZY SELECT COUNT(*) FORM board_hashtags WHERE tag=홍대
}
