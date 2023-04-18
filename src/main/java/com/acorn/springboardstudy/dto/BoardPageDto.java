package com.acorn.springboardstudy.dto;

import lombok.Data;

@Data
public class BoardPageDto {
    private int pageNum=1;
    private int pageSize=10;//한페이지 몇개의 보드?

    enum BoardsType {
        b_id,u_id,post_time,update_time,title,view_count,content
    }
    enum DirectType{
        DESC,ASC,
    }

    //필드명을 받아서 실행하는 것은 sql injection 에 노출 될 수 있음.
    private BoardsType order= BoardsType.post_time;
    private DirectType direct=DirectType.DESC;
    //SELECT * FORM boards ORDER BY b_id; DROP TABLE boards;//이경우 테이블이 삭제될수 있음.
    //SELECT * FORM boards ORDER BY "b_id; DROP TABLE boards;"//파라미터를 문자열로 표시하면 DROP-이후 쿼리가 실행되지 않음 -> prepareStatement
    //파라미터를 문자열로 표시하면 sql injection 을 방지할 수 있는데 order by는 필드명을 출력해야 하기 때문에 파라미터로 작성 불가능


    private BoardsType searchField;
    private String searchValue;
    private String orderBy;

    public String getOrderBy() {//쿼리로 넘어간 값이 없는 경우 공백으로 지정.
        // //SELECT * FORM boards ORDER BY order desc
        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            //SELECT * FORM boards ORDER BY order null(X)
            //SELECT * FORM boards ORDER BY order (O)
            this.direct=DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return BoardsType.post_time+" " +DirectType.DESC;//SELECT * FORM boards ORDER BY  기본값.
    }
}

