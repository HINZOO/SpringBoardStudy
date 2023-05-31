package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExamGridDto {
    private int eId;
    private String uId;
    private String name;
    private String nation;
    private String city;
    private String postTime;
    private String gender;

}
