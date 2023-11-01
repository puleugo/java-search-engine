package com.megabrain.javasearchengine.util;

public class BasePaginationEntity<T> {
    private T[] items;
    private PaginationMetadataEntity metadata;

    public BasePaginationEntity(T[] searchBookResponses, PaginationMetadataEntity metadata) {
        this.items = searchBookResponses;
        this.metadata = metadata;
    }
}
