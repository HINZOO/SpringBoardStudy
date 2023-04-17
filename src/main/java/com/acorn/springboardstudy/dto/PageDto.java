package com.acorn.springboardstudy.dto;

import lombok.Data;

@Data
public class PageDto {
    private int page=1;
    private int offset=10; //페이지당 몇개를 보여줄것인지
    private int startIndex=0; //페이지마다 몇번글 부터 보여줄건지 //값을 얻을때 사용합니다.
    private String order="post_time"; //어떤걸 기준으로
    private String direct="DESC";//정렬할껀지
    private String searchValue; //무슨내용으로 서치를 할지
    private String searchField;
    //네비게이션의 수 알기
    private int totalRows; //게시글 전체수
    private int lastPage; //페이지네비게이션의 총 갯수이자, 라스트 페이지
    private int prevPage;
    private int nextPage;
    private boolean prev;//페이지위치에 따른 버튼이동을 제어하기 위해 생성
    private boolean next;

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        this.lastPage= (int) Math.ceil((double)totalRows/offset);//결과가 숫자.0으로 나오기때문에 int 전환을 한번 더 해줌.
        this.prevPage=this.page-1;
        this.nextPage=this.page+1;
        this.prev=(this.page>1);
        this.next=(this.page<lastPage);
    }

    public PageDto(){}; //default 생성자

    public int getStartIndex() {
        this.startIndex=(page-1)*offset;
        return this.startIndex;
    }

    public void setSearchValue(String searchValue) {
        if(searchValue.trim().equals(""))this.searchValue = searchValue;
    }

    public void setSearchField(String searchField) {
       if(searchField.trim().equals("")) this.searchField = searchField;
    }
//*default 생성자 없이 생성자를 정의하고
    // @ModelAttribute 로 사용하면 생성자에서 사용하는 기본형 파라미터를 require=true 로 정의함.

/*    public PageDto(int page, int offset, String order, String direct, String search) {
        this.page = page;
        this.offset = offset;
        this.order = order;
        this.direct = direct;
        this.search = search;
    }*/


}