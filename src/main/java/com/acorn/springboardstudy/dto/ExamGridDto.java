package com.acorn.springboardstudy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExamGridDto {
    @JsonProperty("eId")
    private int eId;
    @JsonProperty("uId")
    private String uId;
    private String name;
    private String nation;
    private String city;
    private String postTime;
    private String gender;

}
