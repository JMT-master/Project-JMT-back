package com.jmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto<T> {

    private String error;
    private List<T> data;
    private Page<T> pageableData;


}
