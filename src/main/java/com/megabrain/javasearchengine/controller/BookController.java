package com.megabrain.javasearchengine.controller;

import com.megabrain.javasearchengine.dto.ResponseBookDto;
import com.megabrain.javasearchengine.util.BasePaginationEntity;
import com.megabrain.javasearchengine.util.PaginationMetadataEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/books")
public class BookController {
    @GetMapping()
    public BasePaginationEntity<ResponseBookDto> getPaginationBooks() {
        ResponseBookDto[] responseBookDtos = new ResponseBookDto[0];
        PaginationMetadataEntity paginationMetadataEntity = new PaginationMetadataEntity();
        return new BasePaginationEntity<>(responseBookDtos, paginationMetadataEntity);
    }
}
