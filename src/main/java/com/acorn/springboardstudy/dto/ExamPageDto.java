package com.acorn.springboardstudy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExamPageDto {
    private int pageNum=1;
    private int pageSize=10;

    enum BoardsType {
        e_id,u_id,to_time,from_time,nation,city,name,gender
    }
    enum DirectType{
        DESC,ASC,
    }

    private BoardsType order= BoardsType.e_id;
    private DirectType direct= DirectType.DESC;


    private String orderBy;

    private String uId;
    private String name;
    private String nation;
    private String city;
    private String toTime;
    private String fromTime;
    private String gender;



    public String getOrderBy() {
        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            this.direct= DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return BoardsType.e_id+" " + DirectType.DESC;
    }
}

