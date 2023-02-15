package com.example.electronicstore.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableResponse<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    int totalPages;
    long totalElements;
    boolean isLastPage;
}
