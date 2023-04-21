package com.acorn.springboardstudy.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String toUser;
    private String title;
    private String message;
}
