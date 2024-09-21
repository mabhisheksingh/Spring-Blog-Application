package com.blog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class PagedDTO<T> {
    private Long totalElements;
    private Integer dataSize;
    private  List<T> data;
    private Integer currentPage;
    private Integer totalPages;
    
}
