package com.megabrain.javasearchengine.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private Long id;
    private String name;
    private String author;
    private String publisher;
    private Boolean isRented;

    private Book() {
        id = 0L;
        name = "";
        author = "";
        publisher = "";
        isRented = false;
    }

    private Book(Long id, String name, String author, String publisher, Boolean isRented) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.isRented = isRented;
    }

    public static Book of(Long id, String name, String author, String publisher, Boolean isRented) {
        return new Book(id, name, author, publisher, isRented);
    }
}