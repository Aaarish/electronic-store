package com.example.electronicstore.helper;

import com.example.electronicstore.dto.response.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

public class PageableResponseHelper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> entityPage, Class<V> type){
        List<U> entities = entityPage.getContent();

        List<V> dtos = entities.stream()
                .map(e -> new ModelMapper().map(e, type))
                .collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();

        response.setContent(dtos);
        response.setPageNumber(entityPage.getNumber());
        response.setPageSize(entityPage.getSize());
        response.setTotalPages(entityPage.getTotalPages());
        response.setTotalElements(entityPage.getTotalElements());
        response.setLastPage(entityPage.isLast());

        return response;
    }
}
