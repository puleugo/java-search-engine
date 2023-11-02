package com.megabrain.javasearchengine.util;

import java.util.List;

public class BasePaginationEntity<T> {
    private List<T> items;
    private PaginationMetadataEntity metadata;

    public BasePaginationEntity(List<T> searchBookResponses, PaginationMetadataEntity metadata) {
        this.items = searchBookResponses;
        this.metadata = metadata;
    }

}
