package com.megabrain.javasearchengine.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    @NonNull
    private String name;
    @NonNull
    private String author;
    @NonNull
    private String publisher;
    @NonNull
    private LocalDateTime publishedAt;
    @NonNull
    private Boolean isRented;

    public static CreateBookRequest of(String name, String author, String publisher, LocalDateTime publishedAt, Boolean isRented) {
        CreateBookRequest dto = new CreateBookRequest();
        dto.name = name;
        dto.author = author;
        dto.publisher = publisher;
        dto.publishedAt = publishedAt;
        dto.isRented = isRented;
        return dto;
    }

}
