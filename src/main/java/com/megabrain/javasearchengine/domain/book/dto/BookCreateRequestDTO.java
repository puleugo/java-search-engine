package com.megabrain.javasearchengine.domain.book.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

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

    private LocalDateTime publishedAt;

    private String imageUrl;

    public static BookCreateRequestDTO of(String name, String author, String publisher, String publishedAt,String imageUrl) {
        BookCreateRequestDTO dto = new BookCreateRequestDTO();
        dto.name = name;
        dto.author = author;
        dto.publisher = publisher;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (publishedAt != null && !publishedAt.isEmpty()) {
            dto.publishedAt = LocalDateTime.parse(publishedAt, formatter);
        }

        dto.imageUrl = (imageUrl != null && !imageUrl.isEmpty()) ? imageUrl : dto.imageUrl; // null이거나 빈 문자열이면 기본값을 사용
        return dto;
    }

}
