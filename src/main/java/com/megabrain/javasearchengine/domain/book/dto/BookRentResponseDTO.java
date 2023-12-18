package com.megabrain.javasearchengine.domain.book.dto;

import com.megabrain.javasearchengine.domain.book.model.BookRent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
public class BookRentResponseDTO {
    private String name;
    private String author;
    private String publisher;
    private Long id;
    private String imageUrl;
    private String deadline;

    public static BookRentResponseDTO from(BookRent bookRent) {
        LocalDate now = LocalDate.now();
        LocalDate deadlineDateTime = bookRent.getDeadline();
        long daysBetween = ChronoUnit.DAYS.between(now, deadlineDateTime);

        String deadlineDescription;
        if (daysBetween < 0) {
            deadlineDescription = "연체";
        } else if (daysBetween == 0) {
            deadlineDescription = "오늘 마감";
        } else {
            deadlineDescription = daysBetween + "일 전";
        }

        return new BookRentResponseDTO(
                bookRent.getBook().getName(),
                bookRent.getBook().getAuthor(),
                bookRent.getBook().getPublisher(),
                bookRent.getBook().getId(),
                bookRent.getBook().getImageUrl(),
                deadlineDescription
        );
    }
}
