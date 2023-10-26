package com.jmt.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PagingUtil<T> {

    //페이징을 할 qna나 등등의 list
    private List<T> items;
    //페이징 클래스
    private PagingInfo pagingInfo;

}
