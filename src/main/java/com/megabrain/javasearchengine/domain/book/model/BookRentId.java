package com.megabrain.javasearchengine.domain.book.model;

import java.io.Serializable;

public class BookRentId implements Serializable {
    private Long book;
    private Long user;

    private BookRentId() {}

    private BookRentId(Long bookId, Long userId) {
        this.book = bookId;
        this.user = userId;
    }

    public static BookRentId from(Long bookId, Long userId) {
        return new BookRentId(bookId, userId);
    }
}
