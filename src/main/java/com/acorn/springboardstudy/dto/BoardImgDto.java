package com.acorn.springboardstudy.dto;

import lombok.Data;

@Data
public class BoardImgDto {
    private int biId; //pk (Generate Key)
    private int bId;  //fk board.b_id
    private String imgPath;

}
