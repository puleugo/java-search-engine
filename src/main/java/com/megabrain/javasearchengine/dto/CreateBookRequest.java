package com.megabrain.javasearchengine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBookRequest {
    @NonNull
    private String name;
    @NonNull
    private String author;
    @NonNull
    private String publisher;
    @NonNull
    private Boolean isRented;

    private CreateBookRequest() {
        name = "";
        author = "";
        publisher = "";
        isRented = false;
    }

    public static CreateBookRequest of(String name, String author, String publisher, Boolean isRented) {
        CreateBookRequest dto = new CreateBookRequest();
        dto.name = name;
        dto.author = author;
        dto.publisher = publisher;
        dto.isRented = isRented;
        return dto;
    }

}
