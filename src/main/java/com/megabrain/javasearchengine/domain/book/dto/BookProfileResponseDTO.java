package com.megabrain.javasearchengine.domain.book.dto;

import com.megabrain.javasearchengine.domain.book.model.Book;
import com.megabrain.javasearchengine.domain.book.model.BookAndIsRented;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookProfileResponseDTO {

    private Long id;

    @NonNull
    private String name = "";

    @NonNull
    private String author = "";

    @NonNull
    private String publisher = "";

    @NonNull
    private LocalDateTime publishedAt = LocalDateTime.now();

    @NonNull
    private Boolean isRented = false;

    public static BookProfileResponseDTO from(BookAndIsRented bookAndIsRented) {
        BookProfileResponseDTO dto = new BookProfileResponseDTO();
        Book book = bookAndIsRented.getBook();
        dto.id = book.getId();
        dto.name = book.getName();
        dto.author = book.getAuthor();
        dto.publisher = book.getPublisher();
        dto.publishedAt = book.getPublishedAt();
        dto.isRented = bookAndIsRented.getIsRented();
        return dto;
    }

    public static BookProfileResponseDTO from(Book book) {
        BookProfileResponseDTO dto = new BookProfileResponseDTO();
        dto.id = book.getId();
        dto.name = book.getName();
        dto.author = book.getAuthor();
        dto.publisher = book.getPublisher();
        dto.publishedAt = book.getPublishedAt();
        return dto;
    }
}
