package com.megabrain.javasearchengine.domain.book.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequestDTO {
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

    public static BookCreateRequestDTO of(String name, String author, String publisher, LocalDateTime publishedAt, Boolean isRented) {
        BookCreateRequestDTO dto = new BookCreateRequestDTO();
        dto.name = name;
        dto.author = author;
        dto.publisher = publisher;
        dto.publishedAt = publishedAt;
        dto.isRented = isRented;
        return dto;
    }

}
