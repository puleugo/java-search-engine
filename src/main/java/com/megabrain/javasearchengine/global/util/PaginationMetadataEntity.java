package com.megabrain.javasearchengine.global.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaginationMetadataEntity {
    private Long currentPage;
    private Long itemCount;
    private Long requestSize;
    private Long totalPage;
    private Long totalSize;

    private PaginationMetadataEntity(Long currentPage, Long itemCount, Long requestSize, Long totalPage, Long totalSize) {
        this.currentPage = currentPage;
        this.itemCount = itemCount;
        this.requestSize = requestSize;
        this.totalPage = totalPage;
        this.totalSize = totalSize;
    }

    public static PaginationMetadataEntity of(Long currentPage, Long itemCount, Long requestSize, Long totalPage, Long totalSize) {
        return new PaginationMetadataEntity(currentPage, itemCount, requestSize, totalPage, totalSize);
    }
}
