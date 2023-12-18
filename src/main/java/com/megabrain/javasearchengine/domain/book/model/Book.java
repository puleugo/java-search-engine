package com.megabrain.javasearchengine.domain.book.model;

import com.megabrain.javasearchengine.domain.book.dto.BookCreateRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;// 도서 이름

    @Column(nullable = false)
    private String author; //  도서의 저자

    @Column(nullable = false)
    private String publisher; //출판사

    @Column(nullable = false)
    private Boolean isRented; // 현대 대여중인가

    @Column(nullable = false)
    private String imageUrl;

    @Column(name = "published_at", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime publishedAt;

    public static Book from(BookCreateRequestDTO bookCreateRequestDTO) {
        return Book.builder()
                .name(bookCreateRequestDTO.getName())
                .author(bookCreateRequestDTO.getAuthor())
                .isRented(false)
                .imageUrl(bookCreateRequestDTO.getImageUrl())
                .publisher(bookCreateRequestDTO.getPublisher())
                .publishedAt(LocalDateTime.now())
                .build();
    }
}