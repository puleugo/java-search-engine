package com.megabrain.javasearchengine.domain.book.dto;

import com.google.errorprone.annotations.NoAllocation;
import com.megabrain.javasearchengine.domain.book.dao.BookDao;
import com.megabrain.javasearchengine.domain.book.model.BookAndIsRented;
import com.megabrain.javasearchengine.domain.book.model.BookRent;
import com.megabrain.javasearchengine.domain.user.model.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRentDeadResponseDto {
    private String username;
    private UserRole role;
    private Long id;
    private String name;
    private String author;
    private String publisher;
    private String deadline;

    public static BookRentDeadResponseDto from(BookRent bookRent) {
        return BookRentDeadResponseDto.builder()
                .id(bookRent.getBook().getId())
                .name(bookRent.getBook().getName())
                .author(bookRent.getBook().getAuthor())
                .publisher(bookRent.getBook().getPublisher())
                .username(bookRent.getUser().getUsername())
                .role(bookRent.getUser().getRole())
                .deadline(bookRent.getDeadline().toString())
                .build();

    }
}
