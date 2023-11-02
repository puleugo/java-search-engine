package com.megabrain.javasearchengine.dto;

import com.megabrain.javasearchengine.model.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class BookProfileResponse {

    private Long id;

    @NonNull
    private String name = "";

    @NonNull
    private String author = "";

    @NonNull
    private String publisher = "";

    @NonNull
    private Boolean isRented = false;

    public static BookProfileResponse from(Book book) {
        BookProfileResponse dto = new BookProfileResponse();
        dto.id = book.getId();
        dto.name = book.getName();
        dto.author = book.getAuthor();
        dto.publisher = book.getPublisher();
        dto.isRented = book.getIsRented();
        return dto;
    }
}
