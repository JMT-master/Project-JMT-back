package com.jmt.common;

import lombok.*;

@Setter @Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PagingInfo {

    //현재 페이지
    private int currentPage;

    //페이지에서 보여줄 글 갯수?
    private int pageSize;
    //전체 페이지 갯수
    private int totalPages;
    //전체 아이템 갯수
    private Long totalItems;
    //다음으로 넘어가기
    private boolean hasNext;
    //이전으로 넘어가기
    private boolean hasPrevious;
}
