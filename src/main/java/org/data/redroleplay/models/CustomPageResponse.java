package org.data.redroleplay.models;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class CustomPageResponse <E,DTO> {

    private List<DTO> content;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;


    public CustomPageResponse(Page<E> page , Class<DTO> dtoClass) {
        ModelMapper modelMapper = new ModelMapper();
        this.content = page.getContent().stream().map(entity -> modelMapper.map(entity, dtoClass)).toList();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
    }

    public CustomPageResponse(Page<E> page , Class<DTO> dtoClass , ModelMapper modelMapper) {
        this.content = page.getContent().stream().map(entity -> modelMapper.map(entity, dtoClass)).toList();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
    }
}
